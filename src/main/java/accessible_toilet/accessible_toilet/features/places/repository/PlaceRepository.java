package accessible_toilet.accessible_toilet.features.places.repository;

import accessible_toilet.accessible_toilet.features.places.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PlaceRepository extends JpaRepository<Place, UUID> {

    // Быстрый geo-поиск через PostGIS: используем сгенерированную колонку `location`
    @Query(value = """
      SELECT * FROM places
      WHERE status = 'ACTIVE'
        AND ST_DWithin(location, ST_SetSRID(ST_MakePoint(:lon,:lat),4326)::geography, :radius)
      ORDER BY ST_Distance(location, ST_SetSRID(ST_MakePoint(:lon,:lat),4326)::geography)
      LIMIT :limit
      """, nativeQuery = true)
    List<Place> findActiveWithin(@Param("lat") double lat,
                                 @Param("lon") double lon,
                                 @Param("radius") int radiusMeters,
                                 @Param("limit") int limit);
}
