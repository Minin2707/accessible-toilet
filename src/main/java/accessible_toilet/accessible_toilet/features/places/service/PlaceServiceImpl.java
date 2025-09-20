package accessible_toilet.accessible_toilet.features.places.service;

import accessible_toilet.accessible_toilet.common.exception.NotFoundException;
import accessible_toilet.accessible_toilet.features.places.domain.Place;
import accessible_toilet.accessible_toilet.features.places.domain.PlaceMedia;
import accessible_toilet.accessible_toilet.features.places.dto.CreatePlaceRequest;
import accessible_toilet.accessible_toilet.features.places.dto.PlaceDetailResponse;
import accessible_toilet.accessible_toilet.features.places.dto.PlaceShortResponse;
import accessible_toilet.accessible_toilet.features.places.mapper.PlaceMapper;
import accessible_toilet.accessible_toilet.features.places.repository.PlaceMediaRepository;
import accessible_toilet.accessible_toilet.features.places.repository.PlaceRepository;
import accessible_toilet.accessible_toilet.features.users.domain.User;
import accessible_toilet.accessible_toilet.features.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Реализация PlaceService: сохраняет место, медиа и выполняет гео-поиск.
 * Методы:
 * - createPlace: проверяем автора, маппим и сохраняем место, привязываем медиа по s3Key
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepo;
    private final PlaceMediaRepository mediaRepo;
    private final UserRepository userRepo;
    private final PlaceMapper mapper;

    @Override
    public UUID createPlace(CreatePlaceRequest req, UUID authorId) {
        User author = userRepo.findById(authorId)
                .orElseThrow(() -> new NotFoundException("User not found: " + authorId));

        Place place = mapper.toEntity(req);
        if (place.getId() == null) place.setId(UUID.randomUUID());
        place.setAuthor(author);
        place.setStatus(Place.Status.PENDING);
        Place saved = placeRepo.save(place);

        for (String key : req.photoKeys()) {
            PlaceMedia m = new PlaceMedia();
            m.setId(UUID.randomUUID());
            m.setPlace(saved);
            m.setS3Key(key);
            mediaRepo.save(m);
        }
        return saved.getId();
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PlaceShortResponse> findNearby(double lat, double lon, int radius, int limit) {
        return placeRepo.findActiveWithin(lat, lon, radius, limit)
                .stream().map(mapper::toShort).toList();
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public PlaceDetailResponse get(UUID id) {
        Place p = placeRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Place not found: " + id));
        // На MVP вернём пустые URL; позже — пресайны из S3 по s3Key.
        List<String> urls = List.of();
        return mapper.toDetail(p, urls);
    }
}
