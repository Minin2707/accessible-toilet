package accessible_toilet.accessible_toilet.features.places.web;

import accessible_toilet.accessible_toilet.features.ratings.dto.RateRequest;
import accessible_toilet.accessible_toilet.features.ratings.service.RatingService;
import accessible_toilet.accessible_toilet.features.verifications.dto.VerifyRequest;
import accessible_toilet.accessible_toilet.features.verifications.service.VerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Действия над конкретным местом: верификация и оценка.
 * Требуется X-User-Id — текущий пользователь.
 * Методы:
 * - verify: Подтвердить/отклонить место (по одному голосу на пользователя).
 * - rate: Поставить/обновить оценку месту.
 */
@RestController
@RequestMapping("/api/places/{id}")
@RequiredArgsConstructor
public class PlaceActionsController {

    private final VerificationService verificationService;
    private final RatingService ratingService;

    @PutMapping("/verify")
    public void verify(@PathVariable UUID id,
                       @Valid @RequestBody VerifyRequest req,
                       @RequestHeader("X-User-Id") UUID userId) {
        verificationService.verify(id, userId, req);
    }

    @PostMapping("/rate")
    public void rate(@PathVariable UUID id,
                     @Valid @RequestBody RateRequest req,
                     @RequestHeader("X-User-Id") UUID userId) {
        ratingService.rate(id, userId, req);
    }
}