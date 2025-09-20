package accessible_toilet.accessible_toilet.features.places.service;

import accessible_toilet.accessible_toilet.features.places.dto.CreatePlaceRequest;
import accessible_toilet.accessible_toilet.features.places.dto.PlaceDetailResponse;
import accessible_toilet.accessible_toilet.features.places.dto.PlaceShortResponse;

import java.util.List;
import java.util.UUID;

/**
 * Бизнес-логика по местам: создание, поиск рядом, получение деталей.
 * req -  данные места и ключи фото
 * authorId - автор (UUID пользователя)
 * @return идентификатор созданного места
 * Методы:
 * - createPlace: Создать новое место в статусе PENDING.
 * - findNearby: Найти активные места в радиусе от заданной точки.
 * - get: Получить подробную информацию о месте.
 */
public interface PlaceService {
    UUID createPlace(CreatePlaceRequest req, UUID authorId);
    List<PlaceShortResponse> findNearby(double lat, double lon, int radiusMeters, int limit);
    PlaceDetailResponse get(UUID id);
}
