package accessible_toilet.accessible_toilet.features.places.port;

import accessible_toilet.accessible_toilet.features.places.domain.Place;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlaceRepositoryPort {
    Place save(Place place);

    Optional<Place> findById(UUID id);

    List<Place> findActiveWithin(double lat, double lon, int radiusMeters, int limit);
}
