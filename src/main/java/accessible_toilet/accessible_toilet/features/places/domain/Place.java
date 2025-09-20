package accessible_toilet.accessible_toilet.features.places.domain;

import accessible_toilet.accessible_toilet.features.users.domain.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

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
