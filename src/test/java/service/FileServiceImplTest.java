package service;

import cz.kavka.dto.FileDTO;
import cz.kavka.dto.mapper.FileMapper;
import cz.kavka.entity.FileEntity;
import cz.kavka.entity.repository.FileRepository;
import cz.kavka.service.FileServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    @TempDir
    Path tempDir;

    @Mock
    FileRepository fileRepository;

    @Mock
    FileMapper fileMapper;

    //We don't load with @InjectMocks, because want to give our own filePath
    private FileServiceImpl service(String root) {
        return new FileServiceImpl(root, fileRepository, fileMapper);
    }

    // ------- getPdf -------

    @Test
    void getPdf_returnsBytesAndHeaders_whenFileExists() throws IOException {
        var service = service(tempDir.toString());
        byte[] pdf = "%PDF-1.7\n".getBytes();
        Path file = tempDir.resolve("doc.pdf");
        Files.write(file, pdf);

        var resp = service.getPdf("doc.pdf");

        assertThat(resp.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(resp.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_PDF);
        assertThat(resp.getHeaders().getFirst("Content-Disposition"))
                .isEqualTo("inline; filename=doc.pdf");
        assertThat(resp.getBody()).isEqualTo(pdf);
    }

    @Test
    void getPdf_throws_whenBaseDirMissing() {
        var missingDir = tempDir.resolve("nope").toString();
        var service = service(missingDir);

        assertThatThrownBy(() -> service.getPdf("doc.pdf"))
                .isInstanceOf(FileNotFoundException.class)
                .hasMessageContaining("Base PDF directory not found");
    }

    @Test
    void getPdf_throws_whenTargetFileMissing() {
        var service = service(tempDir.toString());
        assertThatThrownBy(() -> service.getPdf("missing.pdf"))
                .isInstanceOf(FileNotFoundException.class)
                .hasMessageContaining("File not found");
    }

    // ------- uploadPdf -------

    @Test
    void uploadPdf_savesFile_andPersistsEntity_whenPdf() throws Exception {
        var service = service(tempDir.toString());
        byte[] pdf = "%PDF-1.4\n".getBytes();

        MultipartFile mf = mock(MultipartFile.class);
        when(mf.getContentType()).thenReturn("application/pdf");
        when(mf.getInputStream()).thenReturn(new ByteArrayInputStream(pdf));

        String originalName = "Dokument 2025.pdf"; // -> normalization: "dokument-2025.pdf"
        service.uploadPdf(mf, originalName);

        Path saved = tempDir.resolve("dokument-2025.pdf");
        assertThat(Files.exists(saved)).isTrue();
        assertThat(Files.readAllBytes(saved)).isEqualTo(pdf);

        ArgumentCaptor<FileEntity> captor = ArgumentCaptor.forClass(FileEntity.class);
        verify(fileRepository).save(captor.capture());
        FileEntity e = captor.getValue();
        assertThat(e.getFileName()).isEqualTo(originalName);
        assertThat(e.getNormalizedFileName()).isEqualTo("dokument-2025.pdf");
        assertThat(e.getUrl()).isEqualTo("/api/files/dokument-2025.pdf");
    }

    @Test
    void uploadPdf_rejectsNonPdf() {
        var service = service(tempDir.toString());
        MultipartFile mf = mock(MultipartFile.class);
        when(mf.getContentType()).thenReturn("image/png");

        assertThatThrownBy(() -> service.uploadPdf(mf, "x.pdf"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Only pdf");
        verifyNoInteractions(fileRepository);
    }

    // ------- getAllFiles -------

    @Test
    void getAllFiles_mapsEntitiesToDtos() {
        var service = service(tempDir.toString());

        FileEntity e1 = new FileEntity(); e1.setId(1L);
        FileEntity e2 = new FileEntity(); e2.setId(2L);
        when(fileRepository.findAll()).thenReturn(List.of(e1, e2));

        FileDTO d1 = new FileDTO(); d1.setId(1L);
        FileDTO d2 = new FileDTO(); d2.setId(2L);
        when(fileMapper.toDTO(e1)).thenReturn(d1);
        when(fileMapper.toDTO(e2)).thenReturn(d2);

        var out = service.getAllFiles();
        assertThat(out).containsExactly(d1, d2);
    }

    // ------- deleteFile -------

    @Test
    void deleteFile_removesFromDisk_andRepository() throws Exception {
        var service = service(tempDir.toString());

        // Create a physical file
        Path f = tempDir.resolve("to-delete.pdf");
        Files.write(f, "%PDF-".getBytes());

        FileEntity entity = new FileEntity();
        entity.setId(42L);
        entity.setNormalizedFileName("to-delete.pdf");

        when(fileRepository.findById(42L)).thenReturn(Optional.of(entity));

        ResponseEntity<?> resp = service.deleteFile(42L);

        assertThat(Files.exists(f)).isFalse();
        verify(fileRepository).delete(entity);
        assertThat(resp.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void deleteFile_throws_whenEntityMissing() {
        var service = service(tempDir.toString());
        when(fileRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteFile(99L))
                .isInstanceOf(EntityNotFoundException.class);
        verify(fileRepository, never()).delete(any());
    }
}
