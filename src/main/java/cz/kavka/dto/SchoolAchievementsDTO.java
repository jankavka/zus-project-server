package cz.kavka.dto;

import cz.kavka.dto.dtosuperclass.NameAndContentDTO;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
public class SchoolAchievementsDTO extends NameAndContentDTO {

    private SchoolYearDTO schoolYear;
}
