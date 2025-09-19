package accessible_toilet.accessible_toilet.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

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