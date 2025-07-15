package cz.kavka.entity.repository;

import cz.kavka.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

//     JPQL doesn't work
//    @Query("SELECT a FROM ArticleEntity a WHERE a.titleImageUrl LIKE CONCAT('%', :albumName, '%')")
//    List<ArticleEntity> getArticles(@Param("albumName") String albumName);

    @Query(value = "SELECT * FROM article WHERE title_image_url LIKE %:albumName%", nativeQuery = true)
    List<ArticleEntity> getArticles(@Param("albumName") String albumName);
}
