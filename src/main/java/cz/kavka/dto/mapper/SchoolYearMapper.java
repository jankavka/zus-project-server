package cz.kavka.dto.mapper;

import cz.kavka.dto.SchoolYearDTO;
import cz.kavka.entity.SchoolYearEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SchoolYearMapper {

    SchoolYearDTO toDTO (SchoolYearEntity schoolYearEntity);

    @Mapping(target = "schoolAchievements", ignore = true)
    SchoolYearEntity toEntity (SchoolYearDTO schoolYearDTO);
}
