package accessible_toilet.accessible_toilet.features.uploads.app;

import accessible_toilet.accessible_toilet.config.S3Props;
import accessible_toilet.accessible_toilet.features.uploads.app.port.ObjectStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;

/**
 * Сервис сценария: сгенерировать ключ и presigned PUT URL.
 * Бизнес-логика формирования ключа остаётся здесь,
 * интеграция с MinIO вынесена в ObjectStoragePort.
 */
@Service
@RequiredArgsConstructor
public class PresignService {

    private final ObjectStoragePort storage; // <-- вместо MinioClient
    private final S3Props props;

    /**
     * Генерирует ключ и presigned PUT URL для загрузки одного файла.
     * @param dir логическая папка (например, "places")
     * @param originalFilename имя файла на клиенте (для удобства)
     * @param contentType ожидаемый MIME (например, "image/jpeg")
     */
    public PresignResult presignPut(String dir, String originalFilename, String contentType) {
        String safeName = (originalFilename == null || originalFilename.isBlank())
                ? "upload"
                : URLEncoder.encode(originalFilename, StandardCharsets.UTF_8);

        String key = "%s/%s/%s".formatted(dir, UUID.randomUUID(), safeName);

        var p = storage.presignPut(key, contentType, props.presignTtlSeconds());
        return new PresignResult(p.key(), p.url(), p.expiresAt());
    }

    /**
     * DTO результата пресайна.
     *
     * @param key       ключ объекта внутри бакета
     * @param url       одноразовый PUT URL для загрузки
     * @param expiresAt момент истечения ссылки
     */
    public record PresignResult(String key, String url, Instant expiresAt) {}
}