package accessible_toilet.accessible_toilet.repository;

import accessible_toilet.accessible_toilet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface UserRepository extends JpaRepository<User, UUID> {}
