package cz.kavka.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.websocket.OnOpen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private LocalDate issuedDate;

    private String imageUrl;






}
