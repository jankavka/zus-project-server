package cz.kavka.entity;

import lombok.Data;

@Data
public abstract class NameAndContentEntity {

    protected Long id;

    protected String title;

    protected String content;
}
