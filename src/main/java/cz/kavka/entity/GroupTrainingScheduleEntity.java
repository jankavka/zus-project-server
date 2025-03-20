package cz.kavka.entity;

import cz.kavka.entity.entitysuperclass.TitleAndContentEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "group_transition_schedule")
public class GroupTrainingScheduleEntity extends TitleAndContentEntity {
}
