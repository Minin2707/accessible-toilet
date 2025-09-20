package accessible_toilet.accessible_toilet.features.verifications.dto;

import accessible_toilet.accessible_toilet.features.verifications.domain.PlaceVerification;
import jakarta.validation.constraints.Size;

public record VerifyRequest(
        PlaceVerification.Decision decision,
        @Size(max = 500)
        String comment
) {}
