package cz.kavka.entity;


import cz.kavka.entity.entitysuperclass.NameAndContentEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "school_fee")
@Getter
@Setter
@AllArgsConstructor
public class SchoolFeeEntity extends NameAndContentEntity {
}
