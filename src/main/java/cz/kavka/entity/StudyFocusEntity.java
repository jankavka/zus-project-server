package cz.kavka.entity;

import cz.kavka.entity.entitysuperclass.NameAndContentEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "study_focus")
@Getter
@Setter
@AllArgsConstructor
public class StudyFocusEntity extends NameAndContentEntity {
}
