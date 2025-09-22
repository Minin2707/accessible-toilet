package accessible_toilet.accessible_toilet.features.uploads.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Запрос на выдачу presigned PUT URL.
 * Клиент отправляет желаемую «директорию», имя файла и MIME-тип.
 * -dir: Логическая директория для ключа, например "places"
 * -filename: Имя файла на клиенте, используется в ключе (для удобства)
 * -contentType: MIME-тип загружаемого файла (например, image/jpeg)
 */
public record PresignRequest(
        /** Логическая директория для ключа, например "places" */
        @NotBlank
        String dir,
        /** Имя файла на клиенте, нужно только для ключа. */
        @NotBlank
        String filename,
        /** MIME типа загрузки, обязателен: image/jpeg | image/png ... */
        @NotBlank
        String contentType
) {}
