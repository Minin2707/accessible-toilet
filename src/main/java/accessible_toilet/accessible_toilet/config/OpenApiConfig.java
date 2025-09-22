package accessible_toilet.accessible_toilet.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Конфигурация OpenAPI/Swagger UI.
 * Добавляет базовую информацию об API и глобальный необязательный хедер X-User-Id
 * для локальной разработки (до подключения реальной аутентификации).
 * -apiInfo(): Базовая мета-информация об API.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Accessible Toilet API")
                        .version("v1")
                        .description("REST API для карты бесплатных туалетов"))
                .servers(List.of(new Server().url("/")));
    }
}
