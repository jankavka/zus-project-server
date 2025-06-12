package cz.kavka.dto;

import lombok.Data;

@Data
public class ImageDTO {

    private Long id;

    private String url;

    private String fileName;

    private AlbumDTO album;
}
