package cz.kavka.entity.entitysuperclass;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NameAndContentEntity {

    @Id
    protected Long id;

    @Column
    protected String title;

    @Column (columnDefinition = "TEXT")
    protected String content;
}
