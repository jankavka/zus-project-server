package cz.kavka.service.serviceinterface;

import cz.kavka.dto.ArticleDTO;

import java.util.List;

public interface ArticleService {

    ArticleDTO createArticle(ArticleDTO articleDTO);

    List<ArticleDTO> getAll();

    ArticleDTO getArticle(Long id);

    ArticleDTO editArticle(ArticleDTO articleDTO, Long id);

    ArticleDTO deleteArticle(Long id);

}
