package accessible_toilet.accessible_toilet.features.places.service;

import accessible_toilet.accessible_toilet.features.places.dto.CreatePlaceRequest;
import accessible_toilet.accessible_toilet.features.places.dto.PlaceDetailResponse;
import accessible_toilet.accessible_toilet.features.places.dto.PlaceShortResponse;

import java.util.List;
import java.util.UUID;

public interface PlaceService {
    UUID createPlace(CreatePlaceRequest req, UUID authorId);
    List<PlaceShortResponse> findNearby(double lat, double lon, int radiusMeters, int limit);
    PlaceDetailResponse get(UUID id);
}
