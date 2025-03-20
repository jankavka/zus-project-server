package cz.kavka.dto;

import cz.kavka.dto.dtosuperclass.TitleAndContentDTO;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
public class SchoolAchievementsDTO extends TitleAndContentDTO {

    private SchoolYearDTO schoolYear;
}
