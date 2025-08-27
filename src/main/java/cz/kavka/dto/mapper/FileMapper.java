package cz.kavka.dto.mapper;

import cz.kavka.dto.FileDTO;
import cz.kavka.entity.FileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileMapper {

    FileDTO toDTO(FileEntity fileEntity);

    @Mapping(target = "normalizedFileName", ignore = true)
    FileEntity toEntity(FileDTO fileDTO);

}
