package cz.kavka.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    // Focal point of the cover crop as CSS object-position, e.g. "50% 25%"; null = centre
    @Pattern(regexp = "^(100|\\d{1,2})% (100|\\d{1,2})%$")
    private String imagePosition;






}
