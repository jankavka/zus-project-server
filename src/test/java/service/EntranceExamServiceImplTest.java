package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kavka.dto.EntranceExamDTO;
import cz.kavka.service.EntranceExamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EntranceExamServiceImplTest {

    @TempDir
    Path tempDir;

    private EntranceExamServiceImpl service;
    private File targetFile;
    private final ObjectMapper checkerMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        targetFile = tempDir.resolve("entrance-exam.json").toFile();
        // inject temp path so the service writes/reads here
        service = new EntranceExamServiceImpl(targetFile.getPath());
    }

    @Test
    void updateEntranceExam_setsIssuedDate_andWritesJson() throws Exception {
        EntranceExamDTO dto = new EntranceExamDTO();
        dto.setHidden(true);

        EntranceExamDTO result = service.updateEntranceExam(dto);

        assertNotNull(result.getIssuedDate());
        assertTrue(targetFile.exists(), "JSON file should be created");

        EntranceExamDTO fromDisk = checkerMapper.readValue(targetFile, EntranceExamDTO.class);
        assertNotNull(fromDisk.getIssuedDate());
        assertTrue(fromDisk.isHidden());
    }

    @Test
    void getEntranceExam_readsFromFile_whenPresent() throws Exception {
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
        assertFalse(targetFile.exists(), "precondition: file should not exist");
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
