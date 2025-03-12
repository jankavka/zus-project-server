package cz.kavka.dto;

import cz.kavka.dto.superclass.NameAndContentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolAchievementsDTO extends NameAndContentDTO {

    private List<String> schoolYears;
}
