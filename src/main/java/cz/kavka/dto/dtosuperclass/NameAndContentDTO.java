package cz.kavka.dto.dtosuperclass;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class NameAndContentDTO {

    protected Long id = 1L;

    protected String title;

    protected String content;


}
