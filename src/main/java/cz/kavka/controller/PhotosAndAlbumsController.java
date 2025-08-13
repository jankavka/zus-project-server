package cz.kavka.controller;

import cz.kavka.dto.AlbumDTO;
import cz.kavka.dto.ImageDTO;
import cz.kavka.service.serviceinterface.PhotoAndAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotosAndAlbumsController {

    private final PhotoAndAlbumService photoService;

    @Autowired
    public PhotosAndAlbumsController(PhotoAndAlbumService photoService) {
        this.photoService = photoService;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/add-photos")
    public ResponseEntity<String> savePhotos(@RequestParam("files") MultipartFile[] files, @RequestParam("albumName") String albumName) throws IOException {
        return photoService.uploadPhotos(files, albumName);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/new-album")
    public AlbumDTO saveAlbum(@RequestBody AlbumDTO albumDTO) throws IOException{
        return photoService.createAlbum(albumDTO);
    }

    @GetMapping("/get-albums")
    public List<AlbumDTO> getAlbums(){
        return photoService.getAllAlbums();
    }

    @GetMapping("/get-images/{albumName}")
    public List<ImageDTO> getImagesFromAlbum(@PathVariable String albumName) {
        return photoService.getAllImagesInAlbum(albumName);
    }

    @GetMapping("/get-one-image/{albumName}")
    public ImageDTO getOneImage(@PathVariable String albumName){
        return photoService.getOneImage(albumName);
    }

    @GetMapping("/all-albums-names")
    public List<String> showAllAlbumNames(){
        return photoService.getAllAlbumsNames();
    }

    @GetMapping("/get-album/{albumName}")
    public AlbumDTO showAlbum(@PathVariable String albumName){
        return photoService.getAlbum(albumName);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete-album/{albumName}")
    public ResponseEntity<AlbumDTO> deleteAlbum(@PathVariable String albumName) throws IOException{
        return photoService.deleteAlbum(albumName);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete-image/{id}")
    public ResponseEntity<ImageDTO> deleteImage(@PathVariable Long id) throws IOException{
        return photoService.deleteImage(id);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/edit-album/{albumName}")
    public AlbumDTO editAlbum(@PathVariable String albumName, @RequestBody AlbumDTO albumDTO) throws IOException{
        return photoService.editAlbumInfo(albumName, albumDTO);
    }

}
