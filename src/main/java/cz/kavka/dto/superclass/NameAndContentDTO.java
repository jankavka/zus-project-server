package cz.kavka.dto.superclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class NameAndContentDTO {

    protected Long id;

    protected String title;

    protected String content;


}
