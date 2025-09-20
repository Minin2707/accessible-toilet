package accessible_toilet.accessible_toilet.features.places.dto;


import java.util.UUID;

public record PlaceShortResponse(
        UUID id,
        String title,
        double lat,
        double lon,
        double avgStars
) {}
