package cz.kavka.entity.repository;

import cz.kavka.entity.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {


    AlbumEntity findByAlbumName( String albumName);

    @Modifying
    @Query(value = "DELETE from album_entity WHERE album_name = :#{#albumName}", nativeQuery = true)
    void deleteAlbumByAlbumName(@Param("albumName") String albumName);
}
