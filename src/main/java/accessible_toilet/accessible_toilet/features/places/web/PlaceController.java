package accessible_toilet.accessible_toilet.features.places.web;

import accessible_toilet.accessible_toilet.features.places.dto.CreatePlaceRequest;
import accessible_toilet.accessible_toilet.features.places.dto.PlaceDetailResponse;
import accessible_toilet.accessible_toilet.features.places.dto.PlaceShortResponse;
import accessible_toilet.accessible_toilet.features.places.service.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST-эндпоинты для работы с местами (создание, поиск рядом, детали).
 * Временная аутентификация: заголовок X-User-Id (UUID).
 * Методы:
 * - create: Создать новое место (статус PENDING). Требуется X-User-Id — автор.
 * - nearby: Поиск активных мест в радиусе от точки (lat/lon).
 * - get: Получить подробности по месту.
 */
@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping
    public ResponseEntity<Map<String, UUID>> create(@Valid @RequestBody CreatePlaceRequest req,
                                                    @RequestHeader("X-User-Id") UUID userId) {
        UUID id = placeService.createPlace(req, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
    }

    @GetMapping
    public List<PlaceShortResponse> nearby(@RequestParam double lat,
                                           @RequestParam double lon,
                                           @RequestParam(defaultValue = "1000") int radius,
                                           @RequestParam(defaultValue = "100") int limit) {
        return placeService.findNearby(lat, lon, radius, limit);
    }

    @GetMapping("/{id}")
    public PlaceDetailResponse get(@PathVariable UUID id) {
        return placeService.get(id);
    }
}
