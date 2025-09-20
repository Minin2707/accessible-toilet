package accessible_toilet.accessible_toilet.features.users.repository;

import accessible_toilet.accessible_toilet.features.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 * Репозиторий пользователей. Базовые CRUD-операции.
 */
public interface UserRepository extends JpaRepository<User, UUID> {}
