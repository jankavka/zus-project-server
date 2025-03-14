package cz.kavka.entity;

import cz.kavka.entity.entitysuperclass.NameAndContentEntity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Data
@Entity
public class SchoolAchievementsEntity extends NameAndContentEntity {

    @ElementCollection
    private List<String> schoolYears;
}
