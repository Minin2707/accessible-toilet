package accessible_toilet.accessible_toilet.features.ratings.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

/**
 * Запрос на выставление/обновление оценки.
 * @param stars Звезды 1..5.
 * @param comment Комментарий (опц., до 1000 символов).
 */
public record RateRequest(
        @Min(1) @Max(5)
        int stars,
        @Size(max = 1000)
        String comment
) {}
