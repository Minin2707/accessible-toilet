package accessible_toilet.accessible_toilet.features.places.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Запрос на создание места.
 * @param title Заголовок места (до 120 символов).
 * @param description Описание (до 2000 символов).
 * @param lat Широта -90..90.
 * @param lon Долгота -180..180.
 * @param photoKeys Ключи загруженных фото в S3/MinIO.
 */
public record CreatePlaceRequest(
        @NotBlank @Size(max = 120)
        String title,
        @Size(max = 2000)
        String description,
        @DecimalMin("-90.0") @DecimalMax("90.0")
        double lat,
        @DecimalMin("-180.0") @DecimalMax("180.0")
        double lon,
        @NotNull @Size(min = 1, max = 6)
        List<@NotBlank String> photoKeys
) {}
