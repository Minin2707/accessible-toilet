package accessible_toilet.accessible_toilet.features.uploads.web;

import accessible_toilet.accessible_toilet.features.uploads.dto.PresignRequest;
import accessible_toilet.accessible_toilet.features.uploads.dto.PresignResponse;
import accessible_toilet.accessible_toilet.storage.PresignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * REST-контроллер для получения одноразовых ссылок (presigned URL) на загрузку файлов напрямую в S3/MinIO.
 * Поток:
 * Клиент вызывает {@code POST /api/uploads/presign} и получает URL + key.
 * Клиент делает {@code PUT} файла по выданному URL.
 * В дальнейшем клиент передаёт {@code key} в бизнес-эндпоинтах (например, при создании Place).
 */
@RestController
@RequestMapping("/api/uploads")
@RequiredArgsConstructor
public class UploadController {

    private final PresignService presignService;

    /**
     * Выдаёт одноразовую ссылку (PUT) для загрузки файла напрямую в S3/MinIO.
     * После успешной загрузки на клиенте — передавай вернувшийся key в CreatePlaceRequest.photoKeys.
     * @param req параметры пресайна (директория, имя файла, MIME-тип)
     * @return ключ объекта, одноразовая ссылка и время истечения
     * @throws Exception если генерация ссылки не удалась
     */
    @PostMapping("/presign")
    public PresignResponse presign(@Valid @RequestBody PresignRequest req) throws Exception {
        var r = presignService.presignPut(req.dir(), req.filename(), req.contentType());
        return new PresignResponse(r.key(), r.url(), r.expiresAt());
    }
}
