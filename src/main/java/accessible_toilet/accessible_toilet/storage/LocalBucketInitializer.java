package accessible_toilet.accessible_toilet.storage;

import accessible_toilet.accessible_toilet.config.S3Props;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@Profile("local") // только в локальном профиле
@RequiredArgsConstructor
public class LocalBucketInitializer {

    private final S3Props props;

    @Bean
    public ApplicationRunner ensureBucket(MinioClient minio) {
        return args -> {
            try {
                boolean exists = minio.bucketExists(BucketExistsArgs.builder().bucket(props.bucket()).build());
                if (!exists) {
                    minio.makeBucket(MakeBucketArgs.builder().bucket(props.bucket()).build());
                    log.info("MinIO bucket '{}' created", props.bucket());
                } else {
                    log.info("MinIO bucket '{}' already exists", props.bucket());
                }
            } catch (Exception e) {
                // не валим приложение — просто логируем, чтобы можно было подняться без MinIO
                log.warn("MinIO bucket check failed: {}", e.getMessage());
            }
        };
    }
}
