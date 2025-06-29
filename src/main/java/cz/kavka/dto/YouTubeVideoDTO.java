package cz.kavka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YouTubeVideoDTO {
    private String title;
    private String description;
    private String videoId;
    private String thumbnailUrl;
}
