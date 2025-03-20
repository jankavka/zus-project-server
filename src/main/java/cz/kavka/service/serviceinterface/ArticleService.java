package cz.kavka.service.serviceinterface;

import cz.kavka.dto.ArticleDTO;

import java.util.List;

public interface ArticleService {

    /**
     * Creates a new article in database
     * @param articleDTO An object with title, content, issued date and id of a new article
     * @return DTO object a new article
     */
    ArticleDTO createArticle(ArticleDTO articleDTO);

    /**
     * Fetches all articles form database
     * @return List of all articles
     */
    List<ArticleDTO> getAll();

    /**
     * Fetches an article with specific id.
     * @param id primary database key
     * @return DTO object with specific article
     */
    ArticleDTO getArticle(Long id);

    /**
     * Edits an article with specific id.
     * @param articleDTO DTO with actual article data
     * @param id primary database key
     * @return DTO object of actualized article
     */
    ArticleDTO editArticle(ArticleDTO articleDTO, Long id);

    /**
     * Removes an article from database
     * @param id primary database key
     * @return DTO object with deleted article data
     */
    ArticleDTO deleteArticle(Long id);

}
