package cz.kavka.service.serviceInterface;

import cz.kavka.dto.ArticleDTO;

import java.util.List;

public interface ArticleService {

    ArticleDTO create(ArticleDTO articleDTO);

    List<ArticleDTO> getAll();

    ArticleDTO getArticle(Long id);

    ArticleDTO edit(ArticleDTO articleDTO, Long id);

    ArticleDTO delete(Long id);

}
