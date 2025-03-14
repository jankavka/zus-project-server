package cz.kavka.entity;


import cz.kavka.entity.entitysuperclass.NameAndContentEntity;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class SchoolFeeEntity extends NameAndContentEntity {
}
