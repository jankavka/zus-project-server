package cz.kavka.dto.mapper;

import cz.kavka.dto.SchoolYearDTO;
import cz.kavka.entity.SchoolYearEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolYearMapper {

    SchoolYearDTO toDTO (SchoolYearEntity schoolYearEntity);

    SchoolYearEntity toEntity (SchoolYearDTO schoolYearDTO);
}
