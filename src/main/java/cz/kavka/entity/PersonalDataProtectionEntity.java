package cz.kavka.entity;

import cz.kavka.entity.superclass.NameAndContentEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonalDataProtectionEntity extends NameAndContentEntity {
}
