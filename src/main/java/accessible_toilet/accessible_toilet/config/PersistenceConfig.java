package accessible_toilet.accessible_toilet.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Общая конфигурация приложения для включения привязки конфигурационных свойств к POJO (S3Props и пр.)
 */
@Configuration
@EnableConfigurationProperties({ S3Props.class })
public class PersistenceConfig {}
