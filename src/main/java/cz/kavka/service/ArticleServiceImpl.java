package cz.kavka.service;

import cz.kavka.dto.ArticleDTO;
import cz.kavka.dto.mapper.ArticleMapper;
import cz.kavka.entity.ArticleEntity;
import cz.kavka.entity.repository.ArticleRepository;
import cz.kavka.service.serviceinterface.ArticleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        ArticleEntity savedEntity = articleRepository.save(articleMapper.toEntity(articleDTO));
        return articleMapper.toDTO(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleDTO> getAll(int limit, int page) {
        return articleRepository.findAll(PageRequest.of(page, limit)
                        .withSort(Sort.Direction.DESC, "id"))
                .stream()
                .map(articleMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ArticleDTO getArticle(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        ArticleEntity articleEntity = articleRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Article with id {} wasn't found", id);
                    return new EntityNotFoundException("Article not found");
                });

        return articleMapper.toDTO(articleEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ArticleDTO> getArticles(String titleImageAlbumName) {
        if (titleImageAlbumName == null || titleImageAlbumName.isBlank()) {
            throw new IllegalArgumentException("Album name cannot be null or blank");
        }
        return articleRepository.getArticles(titleImageAlbumName).stream()
                .map(articleMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public ArticleDTO editArticle(ArticleDTO articleDTO, Long id) {
        if (articleRepository.findById(id).isPresent()) {
            ArticleEntity entity = articleMapper.toEntity(articleDTO);
            entity.setId(id);
            ArticleEntity savedEntity = articleRepository.save(entity);
            return articleMapper.toDTO(savedEntity);
        }
        throw new EntityNotFoundException("Article not found");
    }

    @Transactional
    @Override
    public ArticleDTO deleteArticle(Long id) {
        ArticleEntity articleEntity = articleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        ArticleDTO deletedArticle = articleMapper.toDTO(articleEntity);
        articleRepository.delete(articleEntity);
        return deletedArticle;
    }


}
