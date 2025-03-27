package cz.kavka.dto.mapper;

import cz.kavka.dto.SchoolAchievementsDTO;
import cz.kavka.entity.SchoolAchievementsEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {SchoolYearMapper.class})
public interface SchoolAchievementsMapper {

    SchoolAchievementsEntity toEntity(SchoolAchievementsDTO schoolAchievementsDTO);

    SchoolAchievementsDTO toDTO(SchoolAchievementsEntity schoolAchievementsEntity);
}
