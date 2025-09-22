package accessible_toilet.accessible_toilet.features.uploads.dto;

import java.time.Instant;

/**
 * Ответ с одноразовой ссылкой для загрузки файла и метаданными.
 *
 * @param key       ключ объекта (его нужно сохранить у себя и передать при создании Place)
 * @param url       одноразовый PUT URL, по которому клиент загружает файл
 * @param expiresAt время истечения (когда URL перестанет работать)
 */
public record PresignResponse(
        String key,
        String url,
        Instant expiresAt
) {}
