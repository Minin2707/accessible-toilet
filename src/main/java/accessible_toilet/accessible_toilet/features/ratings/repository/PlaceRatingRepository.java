package accessible_toilet.accessible_toilet.features.ratings.repository;

import accessible_toilet.accessible_toilet.features.ratings.domain.PlaceRating;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface PlaceRatingRepository extends JpaRepository<PlaceRating, UUID> {
    Optional<PlaceRating> findByPlaceIdAndUserId(UUID placeId, UUID userId);

    @Query("select coalesce(avg(r.stars),0) from PlaceRating r where r.place.id = :placeId")
    double avgStars(@Param("placeId") UUID placeId);

    long countByPlaceId(UUID placeId);
}
