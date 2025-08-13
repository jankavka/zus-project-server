package cz.kavka.service.serviceinterface;

import cz.kavka.dto.SchoolAchievementsDTO;
import cz.kavka.dto.SchoolYearDTO;
import cz.kavka.entity.SchoolYearEntity;

import java.util.List;

public interface SchoolAchievementsService {

    SchoolAchievementsDTO createAchievement(SchoolAchievementsDTO schoolAchievementsDTO);

    SchoolAchievementsDTO updateAchievement(Long id, SchoolAchievementsDTO schoolAchievementsDTO);

    SchoolAchievementsDTO getAchievement(Long id);

    List<SchoolAchievementsDTO> getAllAchievements();

    SchoolAchievementsDTO deleteAchievement(Long id);

    List<SchoolAchievementsDTO> getAchievementsByYear(Long yearId);
}
