package cz.kavka.dto;

import lombok.Data;

import java.util.List;


@Data
public class ImageDTO {

    private Long id;

    private String url;

    private String fileName;

    private AlbumDTO album;

    private List<ArticleDTO> articles;

}
