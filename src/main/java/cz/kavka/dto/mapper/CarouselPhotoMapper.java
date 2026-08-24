package cz.kavka.dto.mapper;

import cz.kavka.dto.CarouselPhotoDTO;
import cz.kavka.entity.CarouselPhotoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarouselPhotoMapper {

    CarouselPhotoEntity toEntity(CarouselPhotoDTO carouselPhotoDTO);

    CarouselPhotoDTO toDTO(CarouselPhotoEntity carouselPhotoEntity);
}
