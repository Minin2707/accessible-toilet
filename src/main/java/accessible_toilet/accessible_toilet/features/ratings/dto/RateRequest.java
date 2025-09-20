package accessible_toilet.accessible_toilet.features.ratings.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record RateRequest(
        @Min(1) @Max(5)
        int stars,
        @Size(max = 1000)
        String comment
) {}
