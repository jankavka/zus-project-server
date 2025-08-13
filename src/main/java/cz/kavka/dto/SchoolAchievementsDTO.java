package cz.kavka.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolAchievementsDTO {


    private Long id;

    private String title;

    private String content;

    private Date issuedDate;

    private SchoolYearDTO schoolYear;
}
