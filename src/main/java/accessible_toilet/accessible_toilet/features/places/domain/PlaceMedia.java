package accessible_toilet.accessible_toilet.features.places.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

/**
 * Медиафайл, привязанный к месту (фото). Храним только ключ в S3/MinIO.
 * Поля:
 * - id: Идентификатор медиа.
 * - place: Ссылка на место.
 * - s3Key: Ключ файла в бакете (s3Key).
 * - mime: MIME-тип (опц.).
 * - width, height: Ширина/высота (опц.).
 * - createdAt: Время создания записи.
 */
@Entity
@Table(name = "place_media")
@Getter
@Setter
@NoArgsConstructor
public class PlaceMedia {
    @Id
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="place_id", nullable=false)
    private Place place;
    @Column(name="s3_key", nullable=false, length=512)
    private String s3Key;
    private String mime;
    private Integer width;
    private Integer height;
    @Column(nullable=false, updatable=false)
    private Instant createdAt = Instant.now();
}
