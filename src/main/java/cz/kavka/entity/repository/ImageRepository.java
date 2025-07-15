package cz.kavka.entity.repository;

import cz.kavka.entity.AlbumEntity;
import cz.kavka.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    //Not recommended usage of SpEL -> :#{#album.getId()}
    @Query(value = "SELECT * FROM image_entity WHERE album_id = :#{#album.getId()}", nativeQuery = true)
    List<ImageEntity> findAllByAlbumsEntity(@Param("album") AlbumEntity album);

    @Query(value = "SELECT * FROM image_entity WHERE album_id = :#{#album.getId()} LIMIT 1", nativeQuery = true)
    ImageEntity getOneImage(@Param("album") AlbumEntity album);
}
