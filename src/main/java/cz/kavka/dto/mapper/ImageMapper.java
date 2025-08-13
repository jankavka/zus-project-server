package cz.kavka.dto.mapper;

import cz.kavka.dto.ImageDTO;
import cz.kavka.entity.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageMapper {


    ImageEntity toEntity(ImageDTO imageDTO);

    @Mapping(target = "album", ignore = true)
    ImageDTO toDTO(ImageEntity imageEntity);
}
