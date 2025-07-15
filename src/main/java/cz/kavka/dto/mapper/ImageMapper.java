package cz.kavka.dto.mapper;

import cz.kavka.dto.ImageDTO;
import cz.kavka.entity.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AlbumMapper.class)
public interface ImageMapper {

    @Mapping(target = "articles", ignore = true)
    ImageEntity toEntity(ImageDTO imageDTO);

    @Mapping(target = "articles", ignore = true)
    ImageDTO toDTO(ImageEntity imageEntity);
}
