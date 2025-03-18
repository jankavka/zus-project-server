package cz.kavka.dto.dtosuperclass;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Data Transfer Object (DTO) class for representing data in the application.
 * This class is extended by other classes, which needs same attributes.
 * This DTO has the ID value always set to a fixed value, which ensures that
 * the database table to which this entity is mapped contains only one record.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class NameAndContentDTO {

    protected Long id = 1L;

    protected String title;

    protected String content;

    protected Date issuedDate;


}
