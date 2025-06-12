package cz.kavka.dto.mapper;

import cz.kavka.dto.ImageDTO;
import cz.kavka.entity.ImageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AlbumMapper.class)
public interface ImageMapper {

    ImageEntity toEntity(ImageDTO imageDTO);

    ImageDTO toDTO(ImageEntity imageEntity);
}
