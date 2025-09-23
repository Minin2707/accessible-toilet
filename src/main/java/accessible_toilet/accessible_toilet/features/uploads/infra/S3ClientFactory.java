package accessible_toilet.accessible_toilet.features.uploads.infra;

import accessible_toilet.accessible_toilet.config.S3Props;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Фабрика клиентов S3/MinIO.
 * Создаёт {@link MinioClient} как Spring-бин.
 * В dev-среде гарантирует наличие бакета (создаёт, если нет).
 * - minioClient() - Создаёт и настраивает MinIO клиент. @return сконфигурированный {@link MinioClient}
 */
@Configuration
@RequiredArgsConstructor
public class S3ClientFactory {

    private final S3Props props;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(props.endpoint())
                .credentials(props.accessKey(), props.secretKey())
                .build();
    }
}
