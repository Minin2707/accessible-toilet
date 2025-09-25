package accessible_toilet.accessible_toilet.features.places.repository;

import accessible_toilet.accessible_toilet.features.places.domain.Place;
import accessible_toilet.accessible_toilet.features.places.port.PlaceRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryJpaAdapter implements PlaceRepositoryPort {

    private final PlaceJpaRepository placeRepository;

    @Override
    public Place save(Place place) {
        return placeRepository.save(place);
    }

    @Override
    public Optional<Place> findById(UUID id) {
        return placeRepository.findById(id);
    }

    @Override
    public List<Place> findActiveWithin(double lat, double lon, int radiusMeters, int limit) {
        return placeRepository.findActiveWithin(lat, lon, radiusMeters, limit);
    }
}
