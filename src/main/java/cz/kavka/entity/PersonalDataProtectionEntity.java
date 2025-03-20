package cz.kavka.entity;

import cz.kavka.entity.entitysuperclass.TitleAndContentEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "personal_data_protection")
public class PersonalDataProtectionEntity extends TitleAndContentEntity {
}
