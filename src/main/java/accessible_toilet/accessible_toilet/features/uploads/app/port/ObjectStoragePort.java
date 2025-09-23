package accessible_toilet.accessible_toilet.features.uploads.app.port;

import java.time.Duration;

import java.time.Instant;

public interface ObjectStoragePort {
    /**
     * Сгенерировать одноразовый PUT URL для загрузки объекта по ключу.
     * @param key полный ключ в бакете
     * @param contentType ожидаемый MIME
     * @param ttlSeconds время жизни ссылки (сек)
     * @return результат с url и временем истечения
     */
    PresignResult presignPut(String key, String contentType, int ttlSeconds);

    record PresignResult(String key, String url, Instant expiresAt) {}
}