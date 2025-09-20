package accessible_toilet.accessible_toilet.features.ratings.service;

import accessible_toilet.accessible_toilet.features.ratings.dto.RateRequest;

import java.util.UUID;

public interface RatingService {
    void rate(UUID placeId, UUID userId, RateRequest req);
}
