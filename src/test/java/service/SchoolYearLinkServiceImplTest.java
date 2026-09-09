package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kavka.dto.SchoolYearLinkDTO;
import cz.kavka.service.SchoolYearLinkServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class SchoolYearLinkServiceImplTest {

    @TempDir
    Path tempDir;

    private SchoolYearLinkServiceImpl service;
    private File targetFile;
    private final ObjectMapper checkerMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        targetFile = tempDir.resolve("school-year-link.json").toFile();
        service = new SchoolYearLinkServiceImpl(targetFile.getPath());
    }

    @Test
    void get_returnsEmptyUrl_whenFileMissing() throws Exception {
        assertFalse(targetFile.exists(), "precondition: file should not exist");

        SchoolYearLinkDTO result = service.get();

        assertNotNull(result);
        assertEquals("", result.getUrl());
    }

    @Test
    void get_readsFromFile_whenPresent() throws Exception {
        checkerMapper.writeValue(targetFile, new SchoolYearLinkDTO("https://example.org/rok"));

        SchoolYearLinkDTO read = service.get();

        assertEquals("https://example.org/rok", read.getUrl());
    }

    @Test
    void update_writesJson_andTrimsUrl() throws Exception {
        SchoolYearLinkDTO result = service.update(new SchoolYearLinkDTO("  https://example.org/rok  "));

        assertEquals("https://example.org/rok", result.getUrl());
        assertTrue(targetFile.exists(), "JSON file should be created");

        SchoolYearLinkDTO fromDisk = checkerMapper.readValue(targetFile, SchoolYearLinkDTO.class);
        assertEquals("https://example.org/rok", fromDisk.getUrl());
    }

    @Test
    void update_nullUrl_isStoredAsEmptyString() throws Exception {
        SchoolYearLinkDTO result = service.update(new SchoolYearLinkDTO(null));

        assertEquals("", result.getUrl());

        SchoolYearLinkDTO fromDisk = checkerMapper.readValue(targetFile, SchoolYearLinkDTO.class);
        assertEquals("", fromDisk.getUrl());
    }
}
