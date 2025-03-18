package cz.kavka.entity;

import cz.kavka.entity.entitysuperclass.NameAndContentEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "school_achievements")
@AllArgsConstructor
@NoArgsConstructor
public class SchoolAchievementsEntity extends NameAndContentEntity {

    @ManyToOne
    @JoinColumn(name = "school_year")
    private SchoolYearEntity schoolYear;
}
