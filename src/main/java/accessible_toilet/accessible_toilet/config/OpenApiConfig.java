package accessible_toilet.accessible_toilet.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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

    // Пока мы используем X-User-Id вместо JWT — добавим его глобально как необязательный хедер
    @Bean
    public OpenApiCustomizer addUserHeader() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperations().forEach(op -> op.addParametersItem(
                        new Parameter()
                                .in("header")
                                .name("X-User-Id")
                                .description("Временный идентификатор пользователя (UUID) для локалки")
                                .required(false)
                                .schema(new StringSchema().format("uuid"))
                ))
        );
    }
}
