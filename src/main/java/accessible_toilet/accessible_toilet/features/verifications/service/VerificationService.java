package accessible_toilet.accessible_toilet.features.verifications.service;

import accessible_toilet.accessible_toilet.features.verifications.dto.VerifyRequest;

import java.util.UUID;

/**
 * Бизнес-логика верификации мест (approve/reject).
 * Методы:
 * - verify: Записать решение пользователя по месту и при необходимости обновить статус.
 *           Блокирует самоподтверждение и повторные голоса.
 */
public interface VerificationService {
    void verify(UUID placeId, UUID reviewerId, VerifyRequest req);
}