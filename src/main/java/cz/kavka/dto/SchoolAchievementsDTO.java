package cz.kavka.dto;

import cz.kavka.dto.dtosuperclass.NameAndContentDTO;
import lombok.*;

import java.util.List;

@Data
public class SchoolAchievementsDTO extends NameAndContentDTO {

    private List<String> schoolYears;
}
