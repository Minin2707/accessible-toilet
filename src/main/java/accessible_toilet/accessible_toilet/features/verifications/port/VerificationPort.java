package accessible_toilet.accessible_toilet.features.verifications.port;

import accessible_toilet.accessible_toilet.features.verifications.domain.PlaceVerification;

import java.util.UUID;

public interface VerificationPort {
    boolean existsByPlaceIdAndReviewerId(UUID placeId, UUID reviewerId);

    PlaceVerification save(PlaceVerification verification);

    long countByPlaceIdAndDecision(UUID placeId, PlaceVerification.Decision decision);
}
