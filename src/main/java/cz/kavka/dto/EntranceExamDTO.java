package cz.kavka.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EntranceExamDTO extends TitleAndContentDTO{

    private boolean isHidden;
}
