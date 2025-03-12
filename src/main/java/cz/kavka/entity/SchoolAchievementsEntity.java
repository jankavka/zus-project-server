package cz.kavka.entity;

import cz.kavka.entity.superclass.NameAndContentEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SchoolAchievementsEntity extends NameAndContentEntity {

    private List<String> schoolYears;
}
