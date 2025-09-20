package accessible_toilet.accessible_toilet.features.verifications.repository;

import accessible_toilet.accessible_toilet.features.verifications.domain.PlaceVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Репозиторий решений по верификации мест.
 * Содержит проверки «уже голосовал» и подсчёты approve/reject.
 * Методы:
 * - existsByPlaceIdAndReviewerId: Проверка, голосовал ли уже пользователь за это место.
 * - countByPlaceIdAndDecision: Подсчёт голосов по типу решения.
 */
public interface PlaceVerificationRepository extends JpaRepository<PlaceVerification, UUID> {
    boolean existsByPlaceIdAndReviewerId(UUID placeId, UUID reviewerId);
    long countByPlaceIdAndDecision(UUID placeId, PlaceVerification.Decision decision);
}
