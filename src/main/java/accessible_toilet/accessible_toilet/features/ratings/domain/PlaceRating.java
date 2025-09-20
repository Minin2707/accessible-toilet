package accessible_toilet.accessible_toilet.features.ratings.domain;

import accessible_toilet.accessible_toilet.features.places.domain.Place;
import accessible_toilet.accessible_toilet.features.users.domain.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

/**
 * Оценка места пользователем. Один пользователь может оставить одну оценку,
 * последующие отправки — обновление.
 * Поля:
 * - id: Идентификатор оценки.
 * - place: Оцениваемое место.
 * - user: Автор оценки.
 * - stars: Звезды 1..5.
 * - comment: Текстовый комментарий (опц.).
 * - updatedAt: Создание/обновление оценки.
 */
@Entity
@Table(name = "place_ratings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"place_id","user_id"}))
@Getter
@Setter
@NoArgsConstructor
public class PlaceRating {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="place_id", nullable=false)
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(nullable=false)
    private int stars; // 1..5
    @Column(length=1000)
    private String comment;

    @Column(nullable=false, updatable=false)
    private Instant createdAt = Instant.now();
    private Instant updatedAt;
}