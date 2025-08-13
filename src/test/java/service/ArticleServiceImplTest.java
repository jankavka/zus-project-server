package service;

import cz.kavka.dto.ArticleDTO;
import cz.kavka.dto.mapper.ArticleMapper;
import cz.kavka.entity.ArticleEntity;
import cz.kavka.entity.repository.ArticleRepository;
import cz.kavka.service.ArticleServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @Mock ArticleRepository articleRepository;
    @Mock ArticleMapper articleMapper;

    @InjectMocks ArticleServiceImpl articleService;

    @Captor
    ArgumentCaptor<Pageable> pageableCaptor;

    // ------- createArticle -------
    @Test
    void createArticle_returnsSavedDto() {
        var date = LocalDate.of(2025, 8, 12);
        var inDto = new ArticleDTO(null, "T", "C", date, "URL");
        var toSave = new ArticleEntity(null, "T", "C", date, "URL");
        var saved = new ArticleEntity(1L, "T", "C", date, "URL");
        var outDto = new ArticleDTO(1L, "T", "C", date, "URL");

        when(articleMapper.toEntity(inDto)).thenReturn(toSave);
        when(articleRepository.save(toSave)).thenReturn(saved);
        when(articleMapper.toDTO(saved)).thenReturn(outDto);

        var result = articleService.createArticle(inDto);

        assertEquals(outDto, result);
        verify(articleRepository).save(toSave);
        verifyNoMoreInteractions(articleRepository);
    }

    // ------- getAll -------
    @Test
    void getAll_returnsMappedList_andUsesCorrectPageable() {
        int limit = 2, page = 1; // page index is zero-based
        var e1 = new ArticleEntity(3L, "A", "a", LocalDate.of(2025, 8, 1), "U1");
        var e2 = new ArticleEntity(2L, "B", "b", LocalDate.of(2025, 8, 2), "U2");
        var d1 = new ArticleDTO(3L, "A", "a", e1.getIssuedDate(), "U1");
        var d2 = new ArticleDTO(2L, "B", "b", e2.getIssuedDate(), "U2");

        when(articleRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(e1, e2)));
        when(articleMapper.toDTO(e1)).thenReturn(d1);
        when(articleMapper.toDTO(e2)).thenReturn(d2);

        var result = articleService.getAll(limit, page);

        assertEquals(List.of(d1, d2), result);

        // assert pageable details
        verify(articleRepository).findAll(pageableCaptor.capture());
        var pageable = pageableCaptor.getValue();
        assertEquals(page, pageable.getPageNumber());
        assertEquals(limit, pageable.getPageSize());
        assertTrue(pageable.getSort().getOrderFor("id").isDescending());
    }

    // ------- getArticle -------
    @Test
    void getArticle_returnsDto_whenFound() {
        var e = new ArticleEntity(10L, "T", "C", LocalDate.of(2025, 8, 10), "URL");
        var d = new ArticleDTO(10L, "T", "C", e.getIssuedDate(), "URL");

        when(articleRepository.findById(10L)).thenReturn(Optional.of(e));
        when(articleMapper.toDTO(e)).thenReturn(d);

        var result = articleService.getArticle(10L);

        assertEquals(d, result);
        verify(articleRepository).findById(10L);
        verify(articleMapper).toDTO(e);
    }

    @Test
    void getArticle_throwsWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> articleService.getArticle(null));
        verifyNoInteractions(articleRepository, articleMapper);
    }

    @Test
    void getArticle_throwsNotFound_whenMissing() {
        when(articleRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> articleService.getArticle(99L));
        verify(articleRepository).findById(99L);
        verifyNoInteractions(articleMapper);
    }

    // ------- getArticles by album name -------
    @Test
    void getArticles_returnsMappedList() {
        var e1 = new ArticleEntity(1L, "T1", "C1", LocalDate.of(2025, 7, 1), "ALB");
        var e2 = new ArticleEntity(2L, "T2", "C2", LocalDate.of(2025, 7, 2), "ALB");
        var d1 = new ArticleDTO(1L, "T1", "C1", e1.getIssuedDate(), "ALB");
        var d2 = new ArticleDTO(2L, "T2", "C2", e2.getIssuedDate(), "ALB");

        when(articleRepository.getArticles("Album")).thenReturn(List.of(e1, e2));
        when(articleMapper.toDTO(e1)).thenReturn(d1);
        when(articleMapper.toDTO(e2)).thenReturn(d2);

        var result = articleService.getArticles("Album");

        assertEquals(List.of(d1, d2), result);
        verify(articleRepository).getArticles("Album");
    }

    @Test
    void getArticles_throwsWhenAlbumBlankOrNull() {
        assertThrows(IllegalArgumentException.class, () -> articleService.getArticles(null));
        assertThrows(IllegalArgumentException.class, () -> articleService.getArticles("  "));
        verifyNoInteractions(articleRepository);
    }

    // ------- editArticle -------
    @Test
    void editArticle_updatesAndReturnsDto_whenExists() {
        var id = 5L;
        var dtoIn   = new ArticleDTO(null, "NewTitle", "NewBody", LocalDate.of(2025, 8, 4), "U");
        var mapped  = new ArticleEntity(null, "NewTitle", "NewBody", dtoIn.getIssuedDate(), "U");
        var saved   = new ArticleEntity(id, "NewTitle", "NewBody", dtoIn.getIssuedDate(), "U");
        var dtoOut  = new ArticleDTO(id, "NewTitle", "NewBody", dtoIn.getIssuedDate(), "U");

        when(articleRepository.findById(id)).thenReturn(Optional.of(new ArticleEntity()));
        when(articleMapper.toEntity(dtoIn)).thenReturn(mapped);
        when(articleRepository.save(argThat(e ->
                java.util.Objects.equals(e.getId(), id) &&
                        "NewTitle".equals(e.getTitle())
        ))).thenReturn(saved);
        when(articleMapper.toDTO(saved)).thenReturn(dtoOut);

        var result = articleService.editArticle(dtoIn, id);

        assertEquals(dtoOut, result);
        verify(articleRepository).findById(id);
        verify(articleRepository).save(any(ArticleEntity.class));
    }

    @Test
    void editArticle_throwsNotFound_whenMissing() {
        when(articleRepository.findById(42L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> articleService.editArticle(new ArticleDTO(), 42L));
        verify(articleRepository).findById(42L);
        verify(articleRepository, never()).save(any());
    }

    // ------- deleteArticle -------
    @Test
    void deleteArticle_deletesAndReturnsDto() {
        var e = new ArticleEntity(7L, "T", "C", LocalDate.of(2025, 8, 3), "URL");
        var d = new ArticleDTO(7L, "T", "C", e.getIssuedDate(), "URL");

        when(articleRepository.findById(7L)).thenReturn(Optional.of(e));
        when(articleMapper.toDTO(e)).thenReturn(d);

        var result = articleService.deleteArticle(7L);

        assertEquals(d, result);
        verify(articleRepository).delete(e);
    }

    @Test
    void deleteArticle_throwsNotFound_whenMissing() {
        when(articleRepository.findById(7L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> articleService.deleteArticle(7L));
        verify(articleRepository).findById(7L);
        verify(articleRepository, never()).delete(any());
    }
}