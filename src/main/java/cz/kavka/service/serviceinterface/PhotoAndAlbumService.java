package cz.kavka.service.serviceinterface;

import cz.kavka.dto.AlbumDTO;
import cz.kavka.dto.ImageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface PhotoAndAlbumService {

    /**
     * Creates and album as a directory and uploads there photos
     * @param files An array of photos to upload
     * @param albumName string of a name of a new album
     * @return ResponseEntity with generic string which is string representation of album path
     * @throws IOException while uploaded file is empty
     */
    ResponseEntity<String> uploadPhotos(MultipartFile[] files, String albumName) throws IOException;

    AlbumDTO createAlbum(AlbumDTO albumDTO) throws IOException;

    List<ImageDTO> getAllImagesInAlbum(String albumName);

    List<AlbumDTO> getAllAlbums();

    List<String> getAllAlbumsNames();

    ResponseEntity<AlbumDTO> deleteAlbum(String albumName) throws IOException;

    ResponseEntity<ImageDTO> deleteImage(Long id) throws IOException;

    AlbumDTO editAlbumInfo(String albumName, AlbumDTO albumDTO) throws IOException;

    AlbumDTO getAlbum(String albumName);

    ImageDTO getOneImage(String albumName);




}
