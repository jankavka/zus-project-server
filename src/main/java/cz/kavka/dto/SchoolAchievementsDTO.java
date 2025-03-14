package cz.kavka.dto;

import cz.kavka.dto.dtosuperclass.NameAndContentDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SchoolAchievementsDTO extends NameAndContentDTO {

    private List<String> schoolYears;
}
