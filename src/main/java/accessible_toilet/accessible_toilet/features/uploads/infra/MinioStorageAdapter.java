package accessible_toilet.accessible_toilet.features.uploads.infra;

import accessible_toilet.accessible_toilet.config.S3Props;
import accessible_toilet.accessible_toilet.features.uploads.app.port.ObjectStoragePort;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

/**
 * Адаптер хранилища для MinIO/S3.
 * <p>
 * Реализует порт {@code ObjectStoragePort} и инкапсулирует детали работы с MinIO SDK:
 * генерацию presigned URL для операций PUT/GET. Благодаря этому бизнес-слой
 * зависит от интерфейса порта, а не от конкретного клиента.
 * </p>
 *
 * <h3>Назначение</h3>
 * <ul>
 *   <li>Выдавать одноразовые URL для загрузки (PUT) и чтения (GET) объектов;</li>
 *   <li>Соблюдать TTL ссылок и форсировать ожидаемый {@code Content-Type} для PUT;</li>
 *   <li>Скрывать детали MinIO (bucket, client) от use-case слоёв.</li>
 * </ul>
 *
 * <h3>Особенности</h3>
 * <ul>
 *   <li>Для PUT добавляется query-параметр {@code Content-Type}, чтобы загрузка прошла
 *       только при точном совпадении заголовка в клиентском запросе.</li>
 *   <li>Исключения SDK оборачиваются в {@link RuntimeException} для упрощения
 *       обработки на верхних слоях.</li>
 * </ul>
 */
@Component
public class MinioStorageAdapter implements ObjectStoragePort {

    private final MinioClient minio;
    private final S3Props props;

    public MinioStorageAdapter(MinioClient minio, S3Props props) {
        this.minio = minio;
        this.props = props;
    }

    /**
     * Генерирует одноразовую PUT-ссылку для загрузки объекта по заданному ключу.
     *
     * @param key         полный ключ объекта в бакете (например, {@code places/{uuid}/file.jpg})
     * @param contentType ожидаемый MIME-тип загружаемого файла (должен совпасть при запросе PUT)
     * @param ttlSeconds  время жизни ссылки в секундах
     * @return результат пресайна с ключом, URL и временем истечения
     * @throws RuntimeException при ошибке генерации ссылки в MinIO
     */
    @Override
    public PresignResult presignPut(String key, String contentType, int ttlSeconds) {
        try {
            var url = minio.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(props.bucket())
                            .object(key)
                            .expiry(ttlSeconds)
                            // важно: чтобы PUT в MinIO прошёл, Content-Type в запросе должен совпасть
                            .extraQueryParams(Map.of("Content-Type", contentType))
                            .build()
            );
            return new PresignResult(key, url, Instant.now().plusSeconds(ttlSeconds));
        } catch (Exception e) {
            throw new RuntimeException("Presign PUT failed", e);
        }
    }
}