package accessible_toilet.accessible_toilet.storage;

import accessible_toilet.accessible_toilet.config.S3Props;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * Сервис генерации одноразовых ссылок (presigned URL) для загрузки файлов в S3/MinIO.
 * Выдаёт PUT-ссылку и ключ объекта, который затем сохраняется в доменной модели.
 */
@Service
@RequiredArgsConstructor
public class PresignService {

    private final MinioClient minio;
    private final S3Props props;

    /**
     * Генерирует ключ и presigned PUT URL для загрузки одного файла.
     * @param dir логическая папка (например, "places")
     * @param originalFilename имя файла на клиенте (для удобства)
     * @param contentType ожидаемый MIME (например, "image/jpeg")
     */
    public PresignResult presignPut(String dir, String originalFilename, String contentType) throws Exception {
        String safeName = originalFilename == null || originalFilename.isBlank()
                ? "upload"
                : URLEncoder.encode(originalFilename, StandardCharsets.UTF_8);

        String key = "%s/%s/%s".formatted(
                dir, UUID.randomUUID(), safeName
        );

        var url = minio.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.PUT)
                        .bucket(props.bucket())
                        .object(key)
                        .expiry(props.presignTtlSeconds())
                        .extraQueryParams(Map.of("Content-Type", contentType)) // важен exact match при загрузке
                        .build()
        );

        return new PresignResult(key, url, Instant.now().plusSeconds(props.presignTtlSeconds()));
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
