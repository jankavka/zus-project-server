package cz.kavka.entity.entitysuperclass;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity superclass which is extended by other classes inheriting attributes from this class.
 */
@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitleAndContentEntity {

    @Id
    protected Long id;

    @Column
    protected String title;

    @Column(columnDefinition = "TEXT")
    protected String content;

    @Column
    protected LocalDate issuedDate = LocalDate.now();
}
