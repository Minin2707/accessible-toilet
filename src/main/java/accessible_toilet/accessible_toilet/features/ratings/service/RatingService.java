package accessible_toilet.accessible_toilet.features.ratings.service;

import accessible_toilet.accessible_toilet.features.ratings.dto.RateRequest;

import java.util.UUID;

/**
 * Бизнес-логика оценок мест.
 * Методы:
 * -rate: Создать или обновить оценку места и пересчитать агрегаты (avgStars, ratingsCount).
 */
public interface RatingService {
    void rate(UUID placeId, UUID userId, RateRequest req);
}
