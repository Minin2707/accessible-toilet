package accessible_toilet.accessible_toilet.features.ratings.repository;

import accessible_toilet.accessible_toilet.features.ratings.domain.PlaceRating;
import accessible_toilet.accessible_toilet.features.ratings.port.RatingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PlaceRatingJpaAdapter implements RatingPort {

    private final PlaceRatingJpaRepository repository;

    @Override
    public Optional<PlaceRating> findByPlaceIdAndUserId(UUID placeId, UUID userId) {
        return repository.findByPlaceIdAndUserId(placeId, userId);
    }

    @Override
    public PlaceRating save(PlaceRating rating) {
        return repository.save(rating);
    }

    @Override
    public double avgStars(UUID placeId) {
        return repository.avgStars(placeId);
    }

    @Override
    public long countByPlaceId(UUID placeId) {
        return repository.countByPlaceId(placeId);
    }
}
