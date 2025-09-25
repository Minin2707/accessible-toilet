package accessible_toilet.accessible_toilet.features.ratings.port;

import accessible_toilet.accessible_toilet.features.ratings.domain.PlaceRating;

import java.util.Optional;
import java.util.UUID;

public interface RatingPort {
    Optional<PlaceRating> findByPlaceIdAndUserId(UUID placeId, UUID userId);

    PlaceRating save(PlaceRating rating);

    double avgStars(UUID placeId);

    long countByPlaceId(UUID placeId);
}
