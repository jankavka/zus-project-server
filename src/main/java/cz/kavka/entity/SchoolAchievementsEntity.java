package cz.kavka.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "school_achievements")
@AllArgsConstructor
@NoArgsConstructor
public class SchoolAchievementsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private LocalDate issuedDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "school_year")
    private SchoolYearEntity schoolYear;
}
