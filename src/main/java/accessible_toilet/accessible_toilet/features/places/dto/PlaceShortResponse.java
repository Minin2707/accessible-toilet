package accessible_toilet.accessible_toilet.features.places.dto;


import java.util.UUID;

/**
 * Короткий ответ для списка мест/карты.
 */
public record PlaceShortResponse(
        UUID id,
        String title,
        double lat,
        double lon,
        double avgStars
) {}
