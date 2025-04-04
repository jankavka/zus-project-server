package cz.kavka.dto.mapper;

import cz.kavka.dto.SchoolManagementDTO;
import cz.kavka.entity.SchoolManagementEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolManagementMapper {

    SchoolManagementDTO toDTO(SchoolManagementEntity schoolManagementEntity);

    SchoolManagementEntity toEntity(SchoolManagementDTO schoolManagementDTO);
}
