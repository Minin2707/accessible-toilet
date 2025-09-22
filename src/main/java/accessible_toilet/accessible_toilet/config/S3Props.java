package accessible_toilet.accessible_toilet.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Конфигурация доступа к S3/MinIO.
 * Значения читаются из префикса {@code app.s3} в application-*.yml.
 * Используется для инициализации клиента и генерации presigned URL.
 * @param endpoint S3/MinIO endpoint, например http://localhost:9000
 * @param bucket Имя бакета, куда загружаем файлы
 * @param accessKey Access key для S3/MinIO
 * @param secretKey Secret key для S3/MinIO
 * @param region Регион (для S3), для MinIO может быть произвольным
 * @param presignTtlSeconds Время жизни presigned URL в секундах
 */
@ConfigurationProperties(prefix = "app.s3")
public record S3Props(
        String endpoint,
        String bucket,
        String accessKey,
        String secretKey,
        String region,
        int    presignTtlSeconds
) {}
