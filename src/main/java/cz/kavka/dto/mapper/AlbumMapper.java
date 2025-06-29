package cz.kavka.dto.mapper;

import cz.kavka.dto.AlbumDTO;
import cz.kavka.entity.AlbumEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    AlbumEntity toEntity(AlbumDTO albumDTO);

    @Mapping(target = "images", ignore = true)
    AlbumDTO toDTO(AlbumEntity albumEntity);

}
