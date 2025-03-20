package cz.kavka.entity;

import cz.kavka.entity.entitysuperclass.TitleAndContentEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "history_and_present")
public class HistoryAndPresentEntity extends TitleAndContentEntity {


}
