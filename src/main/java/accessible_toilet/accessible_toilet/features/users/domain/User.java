package accessible_toilet.accessible_toilet.features.users.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * Пользователь приложения.
 * Хранится для привязки авторства, рейтинга и действий (верификации/оценки).
 * Поля
 * - id: Тот же UUID, что и в аутентификации (sub).
 * - displayName: Отображаемое имя в UI.
 * - avatarUrl: Ссылка на аватар (может быть null).
 * - rating: Кэш рейтинга пользователя (считается по событиям).
 * - createdAt: Время регистрации записи.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private UUID id;                       // = sub из JWT
    @Column(nullable=false, length=120)
    private String displayName;
    @Column(length=512)
    private String avatarUrl;
    @Column(nullable=false)
    private int rating = 0;
    @Column(nullable=false, updatable=false)
    private Instant createdAt = Instant.now();
}
