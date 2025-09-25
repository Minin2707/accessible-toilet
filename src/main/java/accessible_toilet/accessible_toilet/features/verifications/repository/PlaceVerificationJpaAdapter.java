package accessible_toilet.accessible_toilet.features.verifications.repository;

import accessible_toilet.accessible_toilet.features.verifications.domain.PlaceVerification;
import accessible_toilet.accessible_toilet.features.verifications.port.VerificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PlaceVerificationJpaAdapter implements VerificationPort {

    private final PlaceVerificationJpaRepository repository;

    @Override
    public boolean existsByPlaceIdAndReviewerId(UUID placeId, UUID reviewerId) {
        return repository.existsByPlaceIdAndReviewerId(placeId, reviewerId);
    }

    @Override
    public PlaceVerification save(PlaceVerification verification) {
        return repository.save(verification);
    }

    @Override
    public long countByPlaceIdAndDecision(UUID placeId, PlaceVerification.Decision decision) {
        return repository.countByPlaceIdAndDecision(placeId, decision);
    }
}
