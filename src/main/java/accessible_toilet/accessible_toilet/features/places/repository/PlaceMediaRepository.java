package accessible_toilet.accessible_toilet.features.places.repository;

import accessible_toilet.accessible_toilet.features.places.domain.PlaceMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface PlaceMediaRepository extends JpaRepository<PlaceMedia, UUID> {}
