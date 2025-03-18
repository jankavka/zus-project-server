package cz.kavka.service;

import cz.kavka.dto.ArticleDTO;
import cz.kavka.dto.mapper.ArticleMapper;
import cz.kavka.entity.ArticleEntity;
import cz.kavka.entity.repository.ArticleRepository;
import cz.kavka.service.serviceinterface.ArticleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper, ArticleRepository articleRepository) {
        this.articleMapper = articleMapper;
        this.articleRepository = articleRepository;
    }

    @Override
    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        ArticleEntity savedEntity = articleRepository.save(articleMapper.toEntity(articleDTO));
        return articleMapper.toDTO(savedEntity);
    }

    @Override
    public List<ArticleDTO> getAll() {
        return articleRepository.findAll().stream().map(articleMapper::toDTO).toList();
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
    public ArticleDTO editArticle(ArticleDTO articleDTO, Long id) {
        articleDTO.setId(id);
        ArticleEntity savedEntity = articleRepository.save(articleMapper.toEntity(articleDTO));
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
