package cz.kavka.dto.mapper;

import cz.kavka.dto.TeachersDTO;
import cz.kavka.entity.TeachersEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeachersMapper {

    TeachersDTO toDTO (TeachersEntity entity);

    TeachersEntity toEntity (TeachersDTO teachersDTO);
}
