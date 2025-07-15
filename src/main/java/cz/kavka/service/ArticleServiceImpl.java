package cz.kavka.service;

import cz.kavka.dto.ArticleDTO;
import cz.kavka.dto.mapper.ArticleMapper;
import cz.kavka.entity.ArticleEntity;
import cz.kavka.entity.repository.ArticleRepository;
import cz.kavka.entity.repository.ImageRepository;
import cz.kavka.service.serviceinterface.ArticleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;

    private final ArticleRepository articleRepository;

    private final ImageRepository imageRepository;

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper, ArticleRepository articleRepository, ImageRepository imageRepository) {
        this.articleMapper = articleMapper;
        this.articleRepository = articleRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        ArticleEntity savedEntity = articleRepository.save(articleMapper.toEntity(articleDTO));
        return articleMapper.toDTO(savedEntity);
    }

    @Override
    public List<ArticleDTO> getAll(int limit) {
        return articleRepository.findAll(PageRequest.of(0, limit)).stream().map(articleMapper::toDTO).toList();
    }

    @Override
    public ArticleDTO getArticle(Long id) {
        ArticleEntity articleEntity;
        try {
            articleEntity = articleRepository.getReferenceById(id);
        } catch (RuntimeException e) {
            log.warn("Article with id {} wasn't found", id);
            throw new EntityNotFoundException("Article not found");
        }
        return articleMapper.toDTO(articleEntity);
    }

    @Override
    public List<ArticleDTO> getArticles(String titleImageAlbumName) {
        return articleRepository.getArticles(titleImageAlbumName).stream()
                .map(articleMapper::toDTO)
                .toList();
    }

    @Override
    public ArticleDTO editArticle(ArticleDTO articleDTO, Long id) {
        ArticleEntity entity = articleMapper.toEntity(articleDTO);
        entity.setId(id);
        entity.setImage(imageRepository.getReferenceById(articleDTO.getImage().getId()));
        ArticleEntity savedEntity = articleRepository.save(entity);
        return articleMapper.toDTO(savedEntity);
    }

    @Override
    public ArticleDTO deleteArticle(Long id) {
        ArticleEntity articleEntity = articleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        ArticleDTO deletedArticle = articleMapper.toDTO(articleEntity);
        articleRepository.delete(articleEntity);
        return deletedArticle;
    }


}
