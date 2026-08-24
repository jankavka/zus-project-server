package service;

import cz.kavka.dto.CarouselPhotoDTO;
import cz.kavka.dto.mapper.CarouselPhotoMapper;
import cz.kavka.entity.CarouselPhotoEntity;
import cz.kavka.entity.repository.CarouselPhotoRepository;
import cz.kavka.service.CarouselPhotoServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarouselPhotoServiceImplTest {

    @Mock CarouselPhotoRepository carouselPhotoRepository;
    @Mock CarouselPhotoMapper carouselPhotoMapper;

    @TempDir Path tmp;

    CarouselPhotoServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new CarouselPhotoServiceImpl(tmp.toString(), carouselPhotoRepository, carouselPhotoMapper);
    }

    private static MultipartFile mockImage(String originalFilename) throws Exception {
        MultipartFile mf = mock(MultipartFile.class);
        when(mf.isEmpty()).thenReturn(false);
        when(mf.getContentType()).thenReturn("image/jpeg");
        when(mf.getOriginalFilename()).thenReturn(originalFilename);
        when(mf.getInputStream()).thenAnswer(__ -> new ByteArrayInputStream("img".getBytes()));
        return mf;
    }

    // ---------------- uploadPhotos ----------------

    @Test
    void uploadPhotos_savesFile_andEntity_withGivenName() throws Exception {
        MultipartFile mf = mockImage("photo.jpg");

        ArgumentCaptor<CarouselPhotoEntity> captor = ArgumentCaptor.forClass(CarouselPhotoEntity.class);
        when(carouselPhotoRepository.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));
        when(carouselPhotoMapper.toDTO(any(CarouselPhotoEntity.class))).thenReturn(new CarouselPhotoDTO());

        List<CarouselPhotoDTO> result = service.uploadPhotos(new MultipartFile[]{mf}, new String[]{"My Photo"});
        assertEquals(1, result.size());

        CarouselPhotoEntity saved = captor.getValue();
        assertEquals("My Photo", saved.getName());
        assertFalse(saved.isHidden());
        assertTrue(saved.getPhotoUrl().startsWith("/carousel-photos/"));
        assertTrue(saved.getPhotoUrl().endsWith("_photo.jpg"), "should append UUID_ + original name");

        String fileName = saved.getPhotoUrl().substring("/carousel-photos/".length());
        assertTrue(Files.exists(tmp.resolve(fileName)));
    }

    @Test
    void uploadPhotos_defaultsNameToOriginalFilename_whenNameMissing() throws Exception {
        MultipartFile mf = mockImage("summer-concert.jpg");

        ArgumentCaptor<CarouselPhotoEntity> captor = ArgumentCaptor.forClass(CarouselPhotoEntity.class);
        when(carouselPhotoRepository.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));
        when(carouselPhotoMapper.toDTO(any(CarouselPhotoEntity.class))).thenReturn(new CarouselPhotoDTO());

        service.uploadPhotos(new MultipartFile[]{mf}, null);

        assertEquals("summer-concert", captor.getValue().getName());
    }

    @Test
    void uploadPhotos_defaultsNameToOriginalFilename_whenNameBlank() throws Exception {
        MultipartFile mf = mockImage("summer-concert.jpg");

        ArgumentCaptor<CarouselPhotoEntity> captor = ArgumentCaptor.forClass(CarouselPhotoEntity.class);
        when(carouselPhotoRepository.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));
        when(carouselPhotoMapper.toDTO(any(CarouselPhotoEntity.class))).thenReturn(new CarouselPhotoDTO());

        service.uploadPhotos(new MultipartFile[]{mf}, new String[]{"  "});

        assertEquals("summer-concert", captor.getValue().getName());
    }

    @Test
    void uploadPhotos_defaultsNameToOriginalFilename_whenNamesArrayShorterThanFiles() throws Exception {
        MultipartFile mf1 = mockImage("first.jpg");
        MultipartFile mf2 = mockImage("second.jpg");

        ArgumentCaptor<CarouselPhotoEntity> captor = ArgumentCaptor.forClass(CarouselPhotoEntity.class);
        when(carouselPhotoRepository.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));
        when(carouselPhotoMapper.toDTO(any(CarouselPhotoEntity.class))).thenReturn(new CarouselPhotoDTO());

        service.uploadPhotos(new MultipartFile[]{mf1, mf2}, new String[]{"First"});

        List<CarouselPhotoEntity> saved = captor.getAllValues();
        assertEquals("First", saved.get(0).getName());
        assertEquals("second", saved.get(1).getName());
    }

    @Test
    void uploadPhotos_throws_onEmptyFile() {
        MultipartFile mf = mock(MultipartFile.class);
        when(mf.isEmpty()).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> service.uploadPhotos(new MultipartFile[]{mf}, new String[]{"x"}));
        verifyNoInteractions(carouselPhotoRepository);
    }

    @Test
    void uploadPhotos_throws_onNonImage() {
        MultipartFile mf = mock(MultipartFile.class);
        when(mf.isEmpty()).thenReturn(false);
        when(mf.getContentType()).thenReturn("application/pdf");

        assertThrows(IllegalArgumentException.class,
                () -> service.uploadPhotos(new MultipartFile[]{mf}, new String[]{"x"}));
        verifyNoInteractions(carouselPhotoRepository);
    }

    // ---------------- getAllPhotos ----------------

    @Test
    void getAllPhotos_mapsEntities() {
        CarouselPhotoEntity e1 = new CarouselPhotoEntity(); e1.setId(1L);
        CarouselPhotoEntity e2 = new CarouselPhotoEntity(); e2.setId(2L);
        when(carouselPhotoRepository.findAll()).thenReturn(List.of(e1, e2));

        CarouselPhotoDTO d1 = new CarouselPhotoDTO(); d1.setId(1L);
        CarouselPhotoDTO d2 = new CarouselPhotoDTO(); d2.setId(2L);
        when(carouselPhotoMapper.toDTO(e1)).thenReturn(d1);
        when(carouselPhotoMapper.toDTO(e2)).thenReturn(d2);

        assertEquals(List.of(d1, d2), service.getAllPhotos());
    }

    // ---------------- editVisibility ----------------

    @Test
    void editVisibility_updatesAndSaves() {
        CarouselPhotoEntity entity = new CarouselPhotoEntity();
        entity.setId(5L);
        entity.setHidden(false);
        when(carouselPhotoRepository.findById(5L)).thenReturn(Optional.of(entity));
        when(carouselPhotoRepository.save(entity)).thenReturn(entity);

        CarouselPhotoDTO dto = new CarouselPhotoDTO();
        when(carouselPhotoMapper.toDTO(entity)).thenReturn(dto);

        CarouselPhotoDTO result = service.editVisibility(5L, true);

        assertTrue(entity.isHidden());
        assertSame(dto, result);
        verify(carouselPhotoRepository).save(entity);
    }

    @Test
    void editVisibility_throws_whenNotFound() {
        when(carouselPhotoRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.editVisibility(5L, true));
    }

    // ---------------- deletePhoto ----------------

    @Test
    void deletePhoto_removesFile_andDeletesEntity() throws Exception {
        String fileName = "abc_photo.jpg";
        Files.writeString(tmp.resolve(fileName), "x");

        CarouselPhotoEntity entity = new CarouselPhotoEntity();
        entity.setId(9L);
        entity.setPhotoUrl("/carousel-photos/" + fileName);
        when(carouselPhotoRepository.findById(9L)).thenReturn(Optional.of(entity));

        CarouselPhotoDTO dto = new CarouselPhotoDTO();
        when(carouselPhotoMapper.toDTO(entity)).thenReturn(dto);

        CarouselPhotoDTO result = service.deletePhoto(9L);

        assertSame(dto, result);
        assertFalse(Files.exists(tmp.resolve(fileName)));
        verify(carouselPhotoRepository).delete(entity);
    }

    @Test
    void deletePhoto_throws_whenNotFound() {
        when(carouselPhotoRepository.findById(9L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.deletePhoto(9L));
    }
}
