package cz.kavka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class AlbumDTO {

    private Long id;

    private String albumName;

    private String leadPictureUrl;

    // Focal point of the cover crop as CSS object-position, e.g. "50% 25%"; null = centre
    @Pattern(regexp = "^(100|\\d{1,2})% (100|\\d{1,2})%$")
    private String leadPicturePosition;

    private String albumDescription;

    private List<ImageDTO> images;

    @JsonProperty(value = "isHidden")
    private boolean isHidden;
}
