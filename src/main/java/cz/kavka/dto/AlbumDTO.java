package cz.kavka.dto;

import lombok.Data;

import java.util.List;

@Data
public class AlbumDTO {

    private Long id;

    private String albumName;

    private String leadPictureUrl;

    private String albumDescription;

    private List<ImageDTO> images;
}
