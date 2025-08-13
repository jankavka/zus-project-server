package cz.kavka.service;

import cz.kavka.dto.AlbumDTO;
import cz.kavka.dto.ImageDTO;
import cz.kavka.dto.mapper.AlbumMapper;
import cz.kavka.dto.mapper.ImageMapper;
import cz.kavka.entity.AlbumEntity;
import cz.kavka.entity.ImageEntity;
import cz.kavka.entity.repository.AlbumRepository;
import cz.kavka.entity.repository.ImageRepository;
import cz.kavka.service.serviceinterface.PhotoAndAlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Service
public class PhotoAndAlbumServiceImpl implements PhotoAndAlbumService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    private final AlbumRepository albumRepository;

    private final AlbumMapper albumMapper;

    @Autowired
    public PhotoAndAlbumServiceImpl(ImageRepository imageRepository, ImageMapper imageMapper, AlbumMapper albumMapper, AlbumRepository albumRepository) {
        this.imageMapper = imageMapper;
        this.imageRepository = imageRepository;
        this.albumMapper = albumMapper;
        this.albumRepository = albumRepository;
    }

    @Transactional
    @Override
    public ResponseEntity<String> uploadPhotos(MultipartFile[] files, String albumName) throws IOException {
        List<ImageEntity> entities = new ArrayList<>();

        //Path of album consists od upload directory and albumName chose by admin
        Path albumPath = Paths.get(uploadDir, albumName);

        //loop over files in MultipartFile array
        for (MultipartFile file : files) {
            //if file is empty returns 400
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File " + file.getOriginalFilename() + " is empty");
                //if file is not image returns 400
            } else if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
                return ResponseEntity.badRequest().body("Only image files are allowed");
            }

            //unique name of uploaded file consisting od random prefix _ and original file name
            String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            //path of file
            Path fullFilePath = albumPath.resolve(uniqueFileName);
            try (InputStream stream = file.getInputStream()) {
                //copies file content using inputStream to file defined by fullPath
                Files.copy(stream, fullFilePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (EOFException e) {
                ResponseEntity.status(500).body("Upload interrupted. Please try again.");
            }

            // new ImageEntity instance
            ImageEntity entity = new ImageEntity();

            entity.setFileName(uniqueFileName);
            entity.setAlbum(albumRepository.findByAlbumName(albumName));

            entity.setUrl("/uploads/" + albumName + "/" + uniqueFileName);
            entities.add(entity);

            imageRepository.save(entity);

            if (getAllImagesInAlbum(albumName).size() == 1) {
                AlbumEntity currentAlbum = albumMapper.toEntity(getAlbum(albumName));
                currentAlbum.setLeadPictureUrl(entity.getUrl());
                albumRepository.save(currentAlbum);
            }


        }

        //Returns path of uploaded album
        return ResponseEntity.ok(entities.toString());
    }

    @Override
    public AlbumDTO createAlbum(AlbumDTO albumDTO) throws IOException {

        albumDTO.setAlbumName(normalizeAlbumName(albumDTO.getAlbumName()));

        //Path of album consists od upload directory and albumName chose by admin
        Path albumPath = Paths.get(uploadDir, albumDTO.getAlbumName());

        //creates a directory named by albumName. If dir already exists nothing created or thrown
        Files.createDirectories(albumPath);

        //Saving album to database
        AlbumEntity savedEntity = albumRepository.save(albumMapper.toEntity(albumDTO));

        return albumMapper.toDTO(savedEntity);

    }

    @Override
    public List<ImageDTO> getAllImagesInAlbum(String albumName) {
        AlbumEntity albumEntity = albumRepository.findByAlbumName(albumName);

        return imageRepository.findAllByAlbumsEntity(albumEntity)
                .stream()
                .map(imageMapper::toDTO)
                .toList();
    }

    @Override
    public List<AlbumDTO> getAllAlbums() {
        return albumRepository.findAll().stream().map(albumMapper::toDTO).toList();
    }

    @Override
    public List<String> getAllAlbumsNames() {
        return albumRepository.findAll().stream().map(AlbumEntity::getAlbumName).toList();
    }


    @Transactional
    @Override
    public ResponseEntity<AlbumDTO> deleteAlbum(String albumName) throws IOException {

        Path albumPath = Paths.get(uploadDir, albumName);
        AlbumEntity entityToDelete = albumRepository.findByAlbumName(albumName);
        AlbumDTO dtoOfDeletedAlbum = albumMapper.toDTO(entityToDelete);
        System.out.println("Entity do delete: " + entityToDelete);
        albumRepository.delete(entityToDelete);

        try (Stream<Path> paths = Files.walk(albumPath)) {
            List<Path> listOfPaths = paths.sorted(Comparator.reverseOrder()).toList();

            for (Path path : listOfPaths) {
                Files.delete(path);
            }

        } catch (IOException e) {
            throw new IOException("Failed to delete file " + e.getMessage());
        }


        return ResponseEntity.ok(dtoOfDeletedAlbum);
    }

    @Transactional
    @Override
    public ResponseEntity<ImageDTO> deleteImage(Long id) throws IOException {
        ImageEntity image = imageRepository.getReferenceById(id);
        Path imagePath = Path.of(uploadDir, image.getAlbum().getAlbumName(), image.getFileName());

        try {
            Files.delete(imagePath);
        } catch (IOException e) {
            log.info("Error: {}", e.getMessage());
            throw new IOException("Internal server error. Image wasn't deleted.", e);
        }

        imageRepository.delete(image);
        return ResponseEntity.ok(imageMapper.toDTO(image));
    }

    @Transactional
    @Override
    public AlbumDTO editAlbumInfo(String albumName, AlbumDTO albumDTO) throws IOException {

        //normalizes new album (directory) name
        albumDTO.setAlbumName(normalizeAlbumName(albumDTO.getAlbumName()));

        //path from witch data will be moved
        Path source = Path.of(uploadDir, albumName);
        //path as target for moved data
        Path target = Path.of(uploadDir, albumDTO.getAlbumName());

        try {
            //moved data from source to target using a type of StandardCopyOption
            Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
        } catch (FileAlreadyExistsException e) {
            throw new IOException("File name already exists", e);
        }

        //id of edited album
        Long id = albumRepository.findByAlbumName(albumName).getId();

        //entities of images we have to set a new url
        List<ImageEntity> images = imageRepository.findAllByAlbumsEntity(albumRepository.findByAlbumName(albumName));

        //setting a new url and saving into database
        for (ImageEntity image : images) {
            image.setUrl("/uploads/" + albumDTO.getAlbumName() + "/" + image.getFileName());
            imageRepository.save(image);
        }

        //Mapping dto to entity
        AlbumEntity album = albumMapper.toEntity(albumDTO);

        //setting id
        album.setId(id);

        //saving entity into db
        albumRepository.save(album);

        return albumMapper.toDTO(album);
    }

    @Override
    public AlbumDTO getAlbum(String albumName) {
        return albumMapper.toDTO(albumRepository.findByAlbumName(albumName));

    }

    @Override
    public ImageDTO getOneImage(String albumName) {

        AlbumEntity album = albumRepository.findByAlbumName(albumName);
        return imageMapper.toDTO(imageRepository.getOneImage(album));
    }


    private static String normalizeAlbumName(String albumName) {
        return Normalizer.normalize(albumName, Normalizer.Form.NFD)
                .replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}+", "")
                .replaceAll("\\[^a-zA-Z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase()
                .trim();
    }


}
