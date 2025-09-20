package accessible_toilet.accessible_toilet.features.places.domain;

import accessible_toilet.accessible_toilet.features.users.domain.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

/**
 * Точка на карте (туалет), добавленная пользователем.
 * Проходит верификацию перед публикацией.
 * Поля:
 * - Status: Статус модерации/публикации места.
 * - id: Идентификатор места.
 * - title: Короткий заголовок.
 * - description: Подробное описание (опционально).
 * - lat: Геопозиция: широта.
 * - lon: Геопозиция: долгота.
 * - status: Текущий статус публикации.
 * - confirmationsCount: Кол-во подтверждений (approve).
 * - rejectionsCount: Кол-во отклонений (reject).
 * - avgStars: Средняя оценка пользователей (1..5).
 * - ratingsCount: Кол-во оценок.
 * - author: Автор места.
 * - createdAt: Дата создания записи.
 * - updatedAt: Дата последнего обновления (может быть null).
 */
@Entity
@Table(name = "places")
@Getter
@Setter
@NoArgsConstructor
public class Place {
    public enum Status { PENDING, ACTIVE, REJECTED }

    @Id private UUID id;
    @Column(nullable=false, length=120)
    private String title;
    @Column(length=2000)
    private String description;
    @Column(nullable=false)
    private double lat;
    @Column(nullable=false)
    private double lon;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=16)
    private Status status = Status.PENDING;

    @Column(nullable=false)
    private int confirmationsCount = 0;
    @Column(nullable=false)
    private int rejectionsCount = 0;
    @Column(nullable=false)
    private double avgStars = 0;
    @Column(nullable=false)
    private int ratingsCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable=false, updatable=false)
    private Instant createdAt = Instant.now();
    private Instant updatedAt;
}
