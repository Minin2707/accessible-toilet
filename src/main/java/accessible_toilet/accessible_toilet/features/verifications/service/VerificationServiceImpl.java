package accessible_toilet.accessible_toilet.features.verifications.service;

import accessible_toilet.accessible_toilet.common.exception.BadRequestException;
import accessible_toilet.accessible_toilet.common.exception.NotFoundException;
import accessible_toilet.accessible_toilet.features.places.domain.Place;
import accessible_toilet.accessible_toilet.features.places.repository.PlaceRepository;
import accessible_toilet.accessible_toilet.features.users.domain.User;
import accessible_toilet.accessible_toilet.features.users.repository.UserRepository;
import accessible_toilet.accessible_toilet.features.verifications.domain.PlaceVerification;
import accessible_toilet.accessible_toilet.features.verifications.dto.VerifyRequest;
import accessible_toilet.accessible_toilet.features.verifications.repository.PlaceVerificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final PlaceRepository placeRepo;
    private final PlaceVerificationRepository verRepo;
    private final UserRepository userRepo;

    @Override
    public void verify(UUID placeId, UUID reviewerId, VerifyRequest req) {
        Place place = placeRepo.findById(placeId)
                .orElseThrow(() -> new NotFoundException("Place not found: " + placeId));

        if (place.getAuthor().getId().equals(reviewerId))
            throw new BadRequestException("Автор не может подтверждать своё место");

        if (verRepo.existsByPlaceIdAndReviewerId(placeId, reviewerId))
            throw new BadRequestException("Пользователь уже голосовал за место");

        User reviewer = userRepo.findById(reviewerId)
                .orElseThrow(() -> new NotFoundException("User not found: " + reviewerId));

        PlaceVerification v = new PlaceVerification();
        v.setId(UUID.randomUUID());
        v.setPlace(place);
        v.setReviewer(reviewer);
        v.setDecision(req.decision());
        v.setComment(req.comment());
        verRepo.save(v);

        long approves = verRepo.countByPlaceIdAndDecision(placeId, PlaceVerification.Decision.APPROVE);
        long rejects  = verRepo.countByPlaceIdAndDecision(placeId, PlaceVerification.Decision.REJECT);
        place.setConfirmationsCount((int) approves);
        place.setRejectionsCount((int) rejects);
        if (approves >= 2) place.setStatus(Place.Status.ACTIVE);
        if (rejects >= 3)  place.setStatus(Place.Status.REJECTED);
        // placeRepo.save(place) не обязателен — в persistence context объект уже dirty.
    }
}
