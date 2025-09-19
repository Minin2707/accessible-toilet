package accessible_toilet.accessible_toilet.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

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
