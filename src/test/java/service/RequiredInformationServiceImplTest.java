package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kavka.dto.RequiredInformationDTO;
import cz.kavka.service.RequiredInformationServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RequiredInformationServiceImplTest {

    @TempDir
    Path tempDir;

    private File targetFile;
    private RequiredInformationServiceImpl service;
    private final ObjectMapper checkerMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        // Use a file under the temp dir
        targetFile = tempDir.resolve("required-information.json").toFile();
        // Ensure parent dir exists (service doesn't create directories)
        File parent = targetFile.getParentFile();
        if (parent != null) parent.mkdirs();

        // Instantiate the service with the exact path it should use
        service = new RequiredInformationServiceImpl(targetFile.getPath());
    }

    @Test
    void createOrEdit_setsIssuedDate_andWritesFile() throws Exception {
        RequiredInformationDTO dto = new RequiredInformationDTO();

        RequiredInformationDTO result = service.createOrEdit(dto);

        // issuedDate should be set by the service
        assertNotNull(result.getIssuedDate(), "issuedDate should be set");
        assertTrue(targetFile.exists(), "JSON file should be created");

        // Read the file back and verify it contains issuedDate
        RequiredInformationDTO fromDisk = checkerMapper.readValue(targetFile, RequiredInformationDTO.class);
        assertNotNull(fromDisk.getIssuedDate(), "issuedDate should be persisted to file");
    }

    @Test
    void getInfo_readsExistingFile() throws Exception {
        // Prepare known content
        RequiredInformationDTO prepared = new RequiredInformationDTO();
        Date fixed = new Date(1_700_000_000_000L); // fixed timestamp for assertion
        prepared.setIssuedDate(fixed);
        checkerMapper.writeValue(targetFile, prepared);

        RequiredInformationDTO read = service.getInfo();

        assertNotNull(read);
        assertEquals(fixed, read.getIssuedDate(), "Should return the value stored in the file");
    }

    @Test
    void getInfo_throwsIOException_whenFileMissing() {
        // Ensure file is absent
        if (targetFile.exists()) assertTrue(targetFile.delete());

        assertThrows(IOException.class, () -> service.getInfo());
    }

    @Test
    void createOrEdit_throwsIOException_whenParentDirectoryMissing() {
        // Point service to a file inside a non-existent directory
        File missingParentFile = tempDir.resolve("nonexistent").resolve("required-information.json").toFile();
        RequiredInformationServiceImpl localService =
                new RequiredInformationServiceImpl(missingParentFile.getPath());

        RequiredInformationDTO dto = new RequiredInformationDTO();
        // Jackson/ObjectMapper won't create parent directories → expect IOException
        assertThrows(IOException.class, () -> localService.createOrEdit(dto));
    }
}
