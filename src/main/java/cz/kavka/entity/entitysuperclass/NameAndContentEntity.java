package cz.kavka.entity.entitysuperclass;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Superclass which is extended by other classes with the same attributes.
 */
@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NameAndContentEntity {

    @Id
    protected Long id;

    @Column
    protected String title;

    @Column(columnDefinition = "TEXT")
    protected String content;

    @Column
    protected LocalDate issuedDate = LocalDate.now();
}
