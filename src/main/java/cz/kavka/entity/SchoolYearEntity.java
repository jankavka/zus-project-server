package cz.kavka.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "school_year")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolYearEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String schoolYear;

    @OneToMany(mappedBy = "schoolYear")
    private List<SchoolAchievementsEntity> schoolAchievements;
}
