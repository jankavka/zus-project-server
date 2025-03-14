package cz.kavka.entity.entitysuperclass;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NameAndContentEntity {

    @Id
    protected Long id;

    @Column
    protected String title;

    @Column
    protected String content;
}
