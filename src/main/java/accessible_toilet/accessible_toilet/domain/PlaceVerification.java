package accessible_toilet.accessible_toilet.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "place_verifications",
        uniqueConstraints = @UniqueConstraint(columnNames = {"place_id","reviewer_id"}))
@Getter
@Setter
@NoArgsConstructor
public class PlaceVerification {
    public enum Decision { APPROVE, REJECT }

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="place_id", nullable=false)
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reviewer_id", nullable=false)
    private User reviewer;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=8)
    private Decision decision;

    @Column(length=500)
    private String comment;
    @Column(nullable=false, updatable=false)
    private Instant createdAt = Instant.now();
}
