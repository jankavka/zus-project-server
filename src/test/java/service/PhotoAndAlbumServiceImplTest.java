package service;

import cz.kavka.dto.AlbumDTO;
import cz.kavka.dto.ImageDTO;
import cz.kavka.dto.mapper.AlbumMapper;
import cz.kavka.dto.mapper.ImageMapper;
import cz.kavka.entity.AlbumEntity;
import cz.kavka.entity.ImageEntity;
import cz.kavka.entity.repository.AlbumRepository;
import cz.kavka.entity.repository.ImageRepository;
import cz.kavka.service.PhotoAndAlbumServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhotoAndAlbumServiceImplTest {

    @Mock ImageRepository imageRepository;
    @Mock ImageMapper imageMapper;
    @Mock AlbumRepository albumRepository;
    @Mock AlbumMapper albumMapper;

    @TempDir Path tmp;

    PhotoAndAlbumServiceImpl service;

    @BeforeEach
    void setUp() throws Exception {
        service = new PhotoAndAlbumServiceImpl(imageRepository, imageMapper, albumMapper, albumRepository);
        // inject uploadDir (private @Value field) without Spring
        Field f = PhotoAndAlbumServiceImpl.class.getDeclaredField("uploadDir");
        f.setAccessible(true);
        f.set(service, tmp.toString());
    }

    // ---------------- uploadPhotos ----------------

    @Test
    void uploadPhotos_savesFile_entity_and_setsLeadPicture_onFirstImage() throws Exception {
        String albumName = "my-album";
        Path albumDir = tmp.resolve(albumName);
        Files.createDirectories(albumDir); // service does not create it

        // album exists in DB
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setAlbumName(albumName);
        when(albumRepository.findByAlbumName(albumName)).thenReturn(albumEntity);

        // mapper round-trip used by getAlbum() and later toEntity()
        AlbumDTO albumDto = new AlbumDTO();
        albumDto.setAlbumName(albumName);
        when(albumMapper.toDTO(albumEntity)).thenReturn(albumDto);
        when(albumMapper.toEntity(albumDto)).thenReturn(albumEntity);

        // a single image uploaded → after save, service calls getAllImagesInAlbum(...).size() == 1
        PhotoAndAlbumServiceImpl spy = Mockito.spy(service);
        when(spy.getAllImagesInAlbum(albumName)).thenReturn(List.of(new ImageDTO())); // size == 1

        // multipart stub
        MultipartFile mf = mock(MultipartFile.class);
        when(mf.isEmpty()).thenReturn(false);
        when(mf.getContentType()).thenReturn("image/jpeg");
        when(mf.getOriginalFilename()).thenReturn("photo.jpg");
        when(mf.getInputStream()).thenAnswer(__ -> new ByteArrayInputStream("img".getBytes()));

        // capture saved ImageEntity to assert file name & URL
        ArgumentCaptor<ImageEntity> imgCaptor = ArgumentCaptor.forClass(ImageEntity.class);
        when(imageRepository.save(imgCaptor.capture())).thenAnswer(inv -> inv.getArgument(0));

        ResponseEntity<String> resp = spy.uploadPhotos(new MultipartFile[]{mf}, albumName);
        assertEquals(200, resp.getStatusCode().value());

        ImageEntity saved = imgCaptor.getValue();
        assertNotNull(saved);
        assertEquals(albumEntity, saved.getAlbum());
        assertTrue(saved.getFileName().endsWith("_photo.jpg"), "should append UUID_ + original name");
        assertEquals("/uploads/" + albumName + "/" + saved.getFileName(), saved.getUrl());

        // physical file exists
        assertTrue(Files.exists(albumDir.resolve(saved.getFileName())));

        // lead picture set on first image
        String expectedUrl = "/uploads/" + albumName + "/" + saved.getFileName();
        verify(albumRepository).save(argThat(a ->
                expectedUrl.equals(a.getLeadPictureUrl())
        ));
    }

    @Test
    void uploadPhotos_returns400_onEmptyFile() throws Exception {
        MultipartFile mf = mock(MultipartFile.class);
        when(mf.isEmpty()).thenReturn(true);
        when(mf.getOriginalFilename()).thenReturn("x.jpg");

        ResponseEntity<String> resp = service.uploadPhotos(new MultipartFile[]{mf}, "alb");
        assertEquals(400, resp.getStatusCode().value());
        verifyNoInteractions(imageRepository);
    }

    @Test
    void uploadPhotos_returns400_onNonImage() throws Exception {
        MultipartFile mf = mock(MultipartFile.class);
        when(mf.isEmpty()).thenReturn(false);
        when(mf.getContentType()).thenReturn("application/pdf");

        ResponseEntity<String> resp = service.uploadPhotos(new MultipartFile[]{mf}, "alb");
        assertEquals(400, resp.getStatusCode().value());
        verifyNoInteractions(imageRepository);
    }

    // ---------------- createAlbum ----------------

    @Test
    void createAlbum_createsDirectory_andSavesEntity() throws Exception {
        AlbumDTO in = new AlbumDTO();
        in.setAlbumName("Álbum Č test");

        // capture the DTO passed to mapper AFTER normalization
        ArgumentCaptor<AlbumDTO> dtoCaptor = ArgumentCaptor.forClass(AlbumDTO.class);
        when(albumMapper.toEntity(dtoCaptor.capture())).thenReturn(new AlbumEntity());

        AlbumEntity saved = new AlbumEntity();
        saved.setId(1L);
        when(albumRepository.save(any(AlbumEntity.class))).thenReturn(saved);
        when(albumMapper.toDTO(saved)).thenReturn(new AlbumDTO());

        AlbumDTO out = service.createAlbum(in);
        assertNotNull(out);

        String normalized = dtoCaptor.getValue().getAlbumName();
        assertNotNull(normalized);
        assertTrue(Files.isDirectory(tmp.resolve(normalized)));
        verify(albumRepository).save(any(AlbumEntity.class));
    }

    // ---------------- getAllImagesInAlbum / getAllAlbums / names ----------------

    @Test
    void getAllImagesInAlbum_mapsEntities() {
        String albumName = "alb";
        AlbumEntity alb = new AlbumEntity(); alb.setAlbumName(albumName);
        when(albumRepository.findByAlbumName(albumName)).thenReturn(alb);

        ImageEntity e1 = new ImageEntity(); e1.setId(1L);
        ImageEntity e2 = new ImageEntity(); e2.setId(2L);
        when(imageRepository.findAllByAlbumsEntity(alb)).thenReturn(List.of(e1, e2));

        ImageDTO d1 = new ImageDTO(); d1.setId(1L);
        ImageDTO d2 = new ImageDTO(); d2.setId(2L);
        when(imageMapper.toDTO(e1)).thenReturn(d1);
        when(imageMapper.toDTO(e2)).thenReturn(d2);

        var result = service.getAllImagesInAlbum(albumName);
        assertEquals(List.of(d1, d2), result);
    }

    @Test
    void getAllAlbums_mapsEntities() {
        AlbumEntity a1 = new AlbumEntity(); a1.setId(1L);
        AlbumEntity a2 = new AlbumEntity(); a2.setId(2L);
        when(albumRepository.findAll()).thenReturn(List.of(a1, a2));

        AlbumDTO d1 = new AlbumDTO(); d1.setId(1L);
        AlbumDTO d2 = new AlbumDTO(); d2.setId(2L);
        when(albumMapper.toDTO(a1)).thenReturn(d1);
        when(albumMapper.toDTO(a2)).thenReturn(d2);

        assertEquals(List.of(d1, d2), service.getAllAlbums());
    }

    @Test
    void getAllAlbumsNames_returnsNamesOnly() {
        AlbumEntity a1 = new AlbumEntity(); a1.setAlbumName("a");
        AlbumEntity a2 = new AlbumEntity(); a2.setAlbumName("b");
        when(albumRepository.findAll()).thenReturn(List.of(a1, a2));

        assertEquals(List.of("a", "b"), service.getAllAlbumsNames());
    }

    // ---------------- deleteAlbum ----------------

    @Test
    void deleteAlbum_removesDirectory_andDeletesEntity() throws Exception {
        String name = "to-delete";
        Path dir = tmp.resolve(name);
        Files.createDirectories(dir);
        Files.writeString(dir.resolve("pic1.jpg"), "x");
        Files.createDirectories(dir.resolve("nested"));
        Files.writeString(dir.resolve("nested/pic2.jpg"), "y");

        AlbumEntity entity = new AlbumEntity(); entity.setId(99L); entity.setAlbumName(name);
        when(albumRepository.findByAlbumName(name)).thenReturn(entity);

        AlbumDTO dto = new AlbumDTO(); dto.setId(99L); dto.setAlbumName(name);
        when(albumMapper.toDTO(entity)).thenReturn(dto);

        ResponseEntity<AlbumDTO> resp = service.deleteAlbum(name);
        assertEquals(200, resp.getStatusCode().value());
        assertEquals(dto, resp.getBody());
        assertFalse(Files.exists(dir));
        verify(albumRepository).delete(entity);
    }

    // ---------------- deleteImage ----------------

    @Test
    void deleteImage_removesFile_andDeletesEntity() throws Exception {
        String albumName = "alb";
        String fileName = "img.jpg";
        Path dir = tmp.resolve(albumName);
        Files.createDirectories(dir);
        Path imgPath = dir.resolve(fileName);
        Files.writeString(imgPath, "x");

        AlbumEntity alb = new AlbumEntity(); alb.setAlbumName(albumName);
        ImageEntity img = new ImageEntity();
        img.setId(7L); img.setAlbum(alb); img.setFileName(fileName);
        when(imageRepository.getReferenceById(7L)).thenReturn(img);

        ImageDTO outDto = new ImageDTO();
        when(imageMapper.toDTO(img)).thenReturn(outDto);

        ResponseEntity<ImageDTO> resp = service.deleteImage(7L);
        assertEquals(200, resp.getStatusCode().value());
        assertFalse(Files.exists(imgPath));
        verify(imageRepository).delete(img);
    }

    // ---------------- editAlbumInfo ----------------

    @Test
    void editAlbumInfo_movesDir_updatesImageUrls_andSavesAlbumWithSameId() throws Exception {
        String oldName = "old-album";
        String newName = "New Name"; // will be normalized internally
        Path oldDir = tmp.resolve(oldName);
        Files.createDirectories(oldDir);
        Files.writeString(oldDir.resolve("any.txt"), "z");

        // old album found with ID
        AlbumEntity oldAlb = new AlbumEntity();
        oldAlb.setId(123L);
        oldAlb.setAlbumName(oldName);
        when(albumRepository.findByAlbumName(oldName)).thenReturn(oldAlb);

        // images belonging to old album
        ImageEntity img = new ImageEntity();
        img.setId(1L); img.setAlbum(oldAlb); img.setFileName("pic.jpg");
        when(imageRepository.findAllByAlbumsEntity(oldAlb)).thenReturn(List.of(img));

        // mapper round-trip for AlbumDTO -> AlbumEntity -> DTO
        AlbumDTO inDto = new AlbumDTO();
        inDto.setAlbumName(newName);
        AlbumEntity mapped = new AlbumEntity();
        when(albumMapper.toEntity(inDto)).thenReturn(mapped);
        when(albumMapper.toDTO(mapped)).thenReturn(new AlbumDTO());

        AlbumDTO result = service.editAlbumInfo(oldName, inDto);
        assertNotNull(result);

        // verify image URL got updated to /uploads/<normalized-new>/pic.jpg
        verify(imageRepository).save(argThat(e -> {
            String url = e.getUrl();
            assertTrue(url.startsWith("/uploads/"));
            assertTrue(url.endsWith("/" + img.getFileName()));
            String normalized = url.split("/")[2];
            assertTrue(Files.isDirectory(tmp.resolve(normalized)), "normalized target dir should exist");
            return true;
        }));

        // album saved with the same id
        verify(albumRepository).save(argThat(a -> a.getId() != null && a.getId().equals(123L)));
    }

    // ---------------- getAlbum / getOneImage ----------------

    @Test
    void getAlbum_fetchesAndMaps() {
        AlbumEntity entity = new AlbumEntity(); entity.setId(1L);
        AlbumDTO dto = new AlbumDTO(); dto.setId(1L);
        when(albumRepository.findByAlbumName("a")).thenReturn(entity);
        when(albumMapper.toDTO(entity)).thenReturn(dto);

        assertSame(dto, service.getAlbum("a"));
    }

    @Test
    void getOneImage_fetchesOneAndMaps() {
        AlbumEntity entity = new AlbumEntity(); entity.setAlbumName("a");
        ImageEntity img = new ImageEntity();
        ImageDTO dto = new ImageDTO();
        when(albumRepository.findByAlbumName("a")).thenReturn(entity);
        when(imageRepository.getOneImage(entity)).thenReturn(img);
        when(imageMapper.toDTO(img)).thenReturn(dto);

        assertSame(dto, service.getOneImage("a"));
    }
}
