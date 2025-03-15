package cz.kavka.entity;

import cz.kavka.entity.entitysuperclass.NameAndContentEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "personal_data_protection")
public class PersonalDataProtectionEntity extends NameAndContentEntity {
}
