package accessible_toilet.accessible_toilet.features.uploads.app.port;

import java.time.Instant;

/**
 * Результат пресайна (одноразовой ссылки) для доступа к объекту в хранилище.
 * <p>
 * Используется как переносимый DTO на границе порта/адаптера: возвращается
 * реализацией {@code ObjectStoragePort} и далее проксируется в web-слой.
 * </p>
 *
 * @param key       полный ключ объекта внутри бакета (например, {@code places/{uuid}/file.jpg})
 * @param url       одноразовый URL для операции (PUT/GET), содержащий все необходимые подписи
 * @param expiresAt момент времени, когда ссылка перестанет быть валидной
 */
public record Presign(String url, String key, Instant expiresAt) {}
