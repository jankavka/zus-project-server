package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kavka.dto.EntranceExamDTO;
import cz.kavka.service.EntranceExamServiceImpl;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EntranceExamServiceImplTest {

    private EntranceExamServiceImpl service;
    private File targetFile;
    private final ObjectMapper checkerMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        service = new EntranceExamServiceImpl();
        // Mirror the service's hard-coded path
        targetFile = new File("data/entrance-exam.json");
        // Ensure parent dir exists and file is clean
        File parent = targetFile.getParentFile();
        if (parent != null) parent.mkdirs();
        if (targetFile.exists() && !targetFile.delete()) {
            throw new IOException("Cannot clean test file: " + targetFile.getAbsolutePath());
        }
    }

    @AfterEach
    void tearDown() {
        // Optional: clean after test (comment out if you want to inspect file)
        if (targetFile.exists()) targetFile.delete();
    }

    @Test
    void updateEntranceExam_setsIssuedDate_andWritesJson() throws Exception {
        EntranceExamDTO dto = new EntranceExamDTO();
        // assuming your DTO has this property:
        dto.setHidden(true);

        EntranceExamDTO result = service.updateEntranceExam(dto);

        // issuedDate should be set by service
        assertNotNull(result.getIssuedDate(), "issuedDate should be set by service");
        assertTrue(targetFile.exists(), "JSON file should be created");

        // Read file back and verify key fields
        EntranceExamDTO fromDisk = checkerMapper.readValue(targetFile, EntranceExamDTO.class);
        assertNotNull(fromDisk.getIssuedDate());
        assertTrue(fromDisk.isHidden());
    }

    @Test
    void getEntranceExam_readsFromFile_whenPresent() throws Exception {
        // Prepare file by writing DTO (simulate existing content)
        EntranceExamDTO prepared = new EntranceExamDTO();
        prepared.setHidden(false);
        prepared.setIssuedDate(new Date());
        checkerMapper.writeValue(targetFile, prepared);

        EntranceExamDTO read = service.getEntranceExam();

        assertNotNull(read);
        assertEquals(prepared.isHidden(), read.isHidden());
        assertNotNull(read.getIssuedDate());
    }

    @Test
    void getEntranceExam_throwsIOException_whenFileMissing() {
        // Ensure file truly doesn't exist
        if (targetFile.exists()) assertTrue(targetFile.delete());

        assertThrows(IOException.class, () -> service.getEntranceExam());
    }

    @Test
    void isHidden_reflectsStoredValue_true() throws Exception {
        EntranceExamDTO prepared = new EntranceExamDTO();
        prepared.setHidden(true);
        prepared.setIssuedDate(new Date());
        checkerMapper.writeValue(targetFile, prepared);

        assertTrue(service.isHidden());
    }

    @Test
    void isHidden_reflectsStoredValue_false() throws Exception {
        EntranceExamDTO prepared = new EntranceExamDTO();
        prepared.setHidden(false);
        prepared.setIssuedDate(new Date());
        checkerMapper.writeValue(targetFile, prepared);

        assertFalse(service.isHidden());
    }
}
