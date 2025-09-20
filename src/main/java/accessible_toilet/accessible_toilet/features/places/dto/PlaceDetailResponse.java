package accessible_toilet.accessible_toilet.features.places.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record PlaceDetailResponse(
        UUID id,
        String title,
        String description,
        double lat,
        double lon,
        String status,
        double avgStars,
        int ratingsCount,
        List<String> photoUrls,
        UUID authorId,
        Instant createdAt
) {}
