package accessible_toilet.accessible_toilet.features.verifications.repository;

import accessible_toilet.accessible_toilet.features.verifications.domain.PlaceVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlaceVerificationRepository extends JpaRepository<PlaceVerification, UUID> {
    boolean existsByPlaceIdAndReviewerId(UUID placeId, UUID reviewerId);
    long countByPlaceIdAndDecision(UUID placeId, PlaceVerification.Decision decision);
}
