package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kavka.dto.TitleAndContentDTO;
import cz.kavka.service.TitleAndContentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TitleAndContentServiceImplTest {

    @TempDir
    Path tempDir;

    private TitleAndContentServiceImpl service;
    private File targetFile;
    private final ObjectMapper checkerMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        // Use a file inside the sandboxed temp directory
        targetFile = tempDir.resolve("title-and-content.json").toFile();
        // Inject the temp path into the service (no Spring context needed)
        service = new TitleAndContentServiceImpl(targetFile.getPath());
    }

    @Test
    void getContent_readsWholeFile() throws Exception {
        // Prepare JSON on disk with two sections
        Map<String, TitleAndContentDTO> initial = new HashMap<>();
        initial.put("about", makeDto("About us", "<p>Hello</p>"));
        initial.put("contact", makeDto("Contact", "<p>Call us</p>"));
        checkerMapper.writeValue(targetFile, initial);

        Map<String, TitleAndContentDTO> read = service.getContent();

        assertEquals(2, read.size());
        assertEquals("About us", read.get("about").getTitle());
        assertEquals("Contact", read.get("contact").getTitle());
    }

    @Test
    void getSection_returnsPresent_whenKeyExists() throws Exception {
        Map<String, TitleAndContentDTO> initial = Map.of(
                "about", makeDto("About us", "Hi")
        );
        checkerMapper.writeValue(targetFile, initial);

        Optional<TitleAndContentDTO> about = service.getSection("about");

        assertTrue(about.isPresent());
        assertEquals("About us", about.get().getTitle());
    }

    @Test
    void getSection_returnsEmpty_whenKeyMissing() throws Exception {
        Map<String, TitleAndContentDTO> initial = Map.of(
                "about", makeDto("About us", "Hi")
        );
        checkerMapper.writeValue(targetFile, initial);

        Optional<TitleAndContentDTO> missing = service.getSection("news");

        assertTrue(missing.isEmpty());
    }

    @Test
    void updateContent_updatesExistingKey_andPersists_andSetsIssuedDate() throws Exception {
        // Existing content with the same key
        Map<String, TitleAndContentDTO> initial = new HashMap<>();
        initial.put("about", makeDto("Old title", "Old content"));
        checkerMapper.writeValue(targetFile, initial);

        TitleAndContentDTO update = new TitleAndContentDTO();
        update.setTitle("New title");
        update.setContent("<p>New content</p>");

        Map<String, TitleAndContentDTO> result = service.updateContent("about", update);

        // Returned map is updated
        assertEquals("New title", result.get("about").getTitle());
        assertNotNull(result.get("about").getIssuedDate());

        // Disk is updated too
        Map<?, ?> fromDisk = checkerMapper.readValue(targetFile, Map.class);
        @SuppressWarnings("unchecked")
        Map<String, Object> about = (Map<String, Object>) fromDisk.get("about");
        assertEquals("New title", about.get("title"));
        assertNotNull(about.get("issuedDate"));
    }

    @Test
    void updateContent_createsNewKey_whenAbsent() throws Exception {
        // Start with empty JSON object
        checkerMapper.writeValue(targetFile, new HashMap<>());

        TitleAndContentDTO dto = new TitleAndContentDTO();
        dto.setTitle("Fresh");
        dto.setContent("Content");

        Map<String, TitleAndContentDTO> result = service.updateContent("fresh", dto);

        assertTrue(result.containsKey("fresh"));
        assertEquals("Fresh", result.get("fresh").getTitle());
        assertNotNull(result.get("fresh").getIssuedDate());

        Map<?, ?> fromDisk = checkerMapper.readValue(targetFile, Map.class);
        assertTrue(fromDisk.containsKey("fresh"));
    }

    @Test
    void getContent_throwsIOException_whenFileMissing() {
        // Do NOT create the file → service should throw
        assertFalse(targetFile.exists(), "precondition: file should not exist");
        assertThrows(IOException.class, service::getContent);
    }

    // --- helpers ---
    private static TitleAndContentDTO makeDto(String title, String content) {
        TitleAndContentDTO dto = new TitleAndContentDTO();
        dto.setTitle(title);
        dto.setContent(content);
        dto.setIssuedDate(new Date());
        return dto;
    }
}
