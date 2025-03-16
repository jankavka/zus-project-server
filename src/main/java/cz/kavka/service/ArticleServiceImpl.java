package cz.kavka.service;

import cz.kavka.dto.ArticleDTO;
import cz.kavka.dto.mapper.ArticleMapper;
import cz.kavka.entity.ArticleEntity;
import cz.kavka.entity.repository.ArticleRepository;
import cz.kavka.service.serviceInterface.ArticleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ArticleDTO create(ArticleDTO articleDTO) {
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
            throw new EntityNotFoundException("Článek nenalazen");
        }
        return articleMapper.toDTO(articleEntity);
    }

    @Override
    public ArticleDTO edit(ArticleDTO articleDTO, Long id) {
        articleDTO.setId(id);
        ArticleEntity savedEntity = articleRepository.save(articleMapper.toEntity(articleDTO));
        return articleMapper.toDTO(savedEntity);
    }

    @Override
    public ArticleDTO delete(Long id) {
        ArticleEntity articleEntity = articleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        ArticleDTO deletedArticle = articleMapper.toDTO(articleEntity);
        articleRepository.delete(articleEntity);
        return deletedArticle;
    }


}
