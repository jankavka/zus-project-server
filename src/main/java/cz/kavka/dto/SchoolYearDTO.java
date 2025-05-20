package cz.kavka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SchoolYearDTO {

    private Long id;

    private String schoolYear;

    private List<SchoolAchievementsDTO> schoolAchievements;

}
