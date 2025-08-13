package service;

import cz.kavka.service.FileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceImplTest {

    @TempDir
    Path tempDir;

    private FileServiceImpl serviceWithBase(Path base) {
        // IMPORTANT: the implementation concatenates strings, so ensure the base ends with a separator
        String baseStr = base.toString() + File.separator;
        return new FileServiceImpl(baseStr);
    }

    @Test
    void getPdf_returnsBytes_andHeaders_whenFileExists() throws Exception {
        // Arrange: base dir + a PDF file
        Path base = tempDir.resolve("pdf_files");
        Files.createDirectories(base);
        Path pdf = base.resolve("doc.pdf");
        byte[] content = "fake-pdf-content".getBytes();
        Files.write(pdf, content);

        FileServiceImpl service = serviceWithBase(base);

        // Act
        ResponseEntity<byte[]> resp = service.getPdf("doc.pdf");

        // Assert
        assertNotNull(resp);
        assertArrayEquals(content, resp.getBody());
        assertEquals(MediaType.APPLICATION_PDF, resp.getHeaders().getContentType());
        assertEquals("inline; filename=doc.pdf",
                resp.getHeaders().getFirst("Content-Disposition"));
    }

    @Test
    void getPdf_throws_whenBaseDirMissing() {
        // Arrange: base dir does NOT exist
        Path missingBase = tempDir.resolve("does-not-exist");
        FileServiceImpl service = serviceWithBase(missingBase);

        // Act + Assert
        assertThrows(FileNotFoundException.class, () -> service.getPdf("doc.pdf"));
    }

    @Test
    void getPdf_throws_whenBasePathIsAFile() throws Exception {
        // Arrange: base path is a FILE (not directory)
        Path baseFile = tempDir.resolve("not-a-dir.txt");
        Files.writeString(baseFile, "x");
        FileServiceImpl service = serviceWithBase(baseFile);

        // Act + Assert
        assertThrows(FileNotFoundException.class, () -> service.getPdf("doc.pdf"));
    }

    @Test
    void getPdf_throws_whenTargetPdfDoesNotExist() throws Exception {
        // Arrange: base dir exists, but file is missing
        Path base = tempDir.resolve("pdf_files");
        Files.createDirectories(base);
        FileServiceImpl service = serviceWithBase(base);

        // Act + Assert: underlying read will throw
        assertThrows(FileNotFoundException.class, () -> service.getPdf("missing.pdf"));
    }

    @Test
    void uploadPdf_copiesFileToBaseDir() throws Exception {
        // Arrange: base dir exists
        Path base = tempDir.resolve("pdf_files");
        Files.createDirectories(base);
        FileServiceImpl service = serviceWithBase(base);

        // Source file (simulating "filePathFromPc")
        Path src = tempDir.resolve("source.pdf");
        byte[] srcBytes = "hello-pdf".getBytes();
        Files.write(src, srcBytes);

        // Act
        service.uploadPdf("stored.pdf", src.toString());

        // Assert: file exists at base/stored.pdf with same content
        Path dest = base.resolve("stored.pdf");
        assertTrue(Files.exists(dest), "Destination file should exist");
        assertArrayEquals(srcBytes, Files.readAllBytes(dest));
    }

    @Test
    void uploadPdf_throws_whenSourceMissing() throws Exception {
        // Arrange: base dir exists
        Path base = tempDir.resolve("pdf_files");
        Files.createDirectories(base);
        FileServiceImpl service = serviceWithBase(base);

        // Act + Assert
        assertThrows(FileNotFoundException.class,
                () -> service.uploadPdf("stored.pdf", base.resolve("nope.pdf").toString()));
    }
}

