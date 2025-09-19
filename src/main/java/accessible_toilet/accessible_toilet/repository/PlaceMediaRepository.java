package accessible_toilet.accessible_toilet.repository;

import accessible_toilet.accessible_toilet.domain.PlaceMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface PlaceMediaRepository extends JpaRepository<PlaceMedia, UUID> {}
