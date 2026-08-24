package cz.kavka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CarouselPhotoDTO {

    private Long id;

    private String name;

    private String photoUrl;

    @JsonProperty(value = "isHidden")
    private boolean isHidden;
}
