package accessible_toilet.accessible_toilet.features.ratings.service;

import accessible_toilet.accessible_toilet.common.exception.NotFoundException;
import accessible_toilet.accessible_toilet.features.places.domain.Place;
import accessible_toilet.accessible_toilet.features.places.port.PlaceRepositoryPort;
import accessible_toilet.accessible_toilet.features.ratings.domain.PlaceRating;
import accessible_toilet.accessible_toilet.features.ratings.dto.RateRequest;
import accessible_toilet.accessible_toilet.features.ratings.port.RatingPort;
import accessible_toilet.accessible_toilet.features.users.domain.User;
import accessible_toilet.accessible_toilet.features.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final PlaceRepositoryPort placeRepo;
    private final RatingPort ratingRepo;
    private final UserRepository userRepo;

    @Override
    public void rate(UUID placeId, UUID userId, RateRequest req) {
        Place place = placeRepo.findById(placeId)
                .orElseThrow(() -> new NotFoundException("Place not found: " + placeId));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId));

        PlaceRating r = ratingRepo.findByPlaceIdAndUserId(placeId, userId)
                .orElseGet(() -> {
                    PlaceRating nr = new PlaceRating();
                    nr.setId(UUID.randomUUID());
                    nr.setPlace(place);
                    nr.setUser(user);
                    return nr;
                });

        r.setStars(req.stars());
        r.setComment(req.comment());
        ratingRepo.save(r);

        place.setAvgStars(ratingRepo.avgStars(placeId));
        place.setRatingsCount((int) ratingRepo.countByPlaceId(placeId));
    }
}
