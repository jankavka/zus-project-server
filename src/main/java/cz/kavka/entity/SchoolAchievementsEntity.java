package cz.kavka.entity;

import cz.kavka.entity.entitysuperclass.NameAndContentEntity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "school_achievements")
public class SchoolAchievementsEntity extends NameAndContentEntity {

    @ElementCollection
    private List<String> schoolYears;
}
