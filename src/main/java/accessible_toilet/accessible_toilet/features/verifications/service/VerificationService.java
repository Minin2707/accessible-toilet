package accessible_toilet.accessible_toilet.features.verifications.service;

import accessible_toilet.accessible_toilet.features.verifications.dto.VerifyRequest;

import java.util.UUID;

public interface VerificationService {
    void verify(UUID placeId, UUID reviewerId, VerifyRequest req);
}