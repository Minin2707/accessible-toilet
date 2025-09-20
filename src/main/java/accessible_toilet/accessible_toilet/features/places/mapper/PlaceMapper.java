package accessible_toilet.accessible_toilet.features.places.mapper;

import accessible_toilet.accessible_toilet.features.places.domain.Place;
import accessible_toilet.accessible_toilet.features.places.dto.CreatePlaceRequest;
import accessible_toilet.accessible_toilet.features.places.dto.PlaceDetailResponse;
import accessible_toilet.accessible_toilet.features.places.dto.PlaceShortResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Маппер мест: преобразование между Entity и DTO.
 * Используется MapStruct (генерирует реализацию на этапе компиляции).
 * Поля:
 * - toEntity: Создание сущности места из запроса.
 * - toDetail: Детальный ответ с подставленными URL медиа.
 * - toShort: Короткий ответ для списка/карты.
 */
@Mapper(componentModel = "spring")
public interface PlaceMapper {
    Place toEntity(CreatePlaceRequest req);

    @Mapping(target = "status", expression = "java(p.getStatus().name())")
    PlaceDetailResponse toDetail(Place p, List<String> photoUrls);

    PlaceShortResponse toShort(Place p);
}
