package cz.kavka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AlbumDTO {

    private Long id;

    private String albumName;

    private String leadPictureUrl;

    private String albumDescription;

    private List<ImageDTO> images;

    @JsonProperty(value = "isHidden")
    private boolean isHidden;
}
