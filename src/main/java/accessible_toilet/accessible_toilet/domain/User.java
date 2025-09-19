package accessible_toilet.accessible_toilet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private UUID id;                       // = sub из JWT
    @Column(nullable=false, length=120) private String displayName;
    @Column(length=512) private String avatarUrl;
    @Column(nullable=false) private int rating = 0;
    @Column(nullable=false, updatable=false) private Instant createdAt = Instant.now();
}
