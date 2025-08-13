package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kavka.dto.BasicDataDTO;
import cz.kavka.service.BasicDataServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasicDataServiceImplTest {

    @Mock
    ObjectMapper objectMapper;

    @TempDir
    Path tempDir;

    // Helper to build the service with a temp file path
    private BasicDataServiceImpl serviceFor(File target) {
        return new BasicDataServiceImpl(objectMapper, target.getPath());
    }

    @Test
    void createOrEditBasicData_setsIssuedDate_andWritesToFile() throws Exception {
        File target = tempDir.resolve("basic-data.json").toFile();
        var service = serviceFor(target);
        var dto = new BasicDataDTO();

        var result = service.createOrEditBasicData(dto);

        assertNotNull(result.getIssuedDate(), "issuedDate should be set");
        verify(objectMapper).writeValue(target, dto);
        verifyNoMoreInteractions(objectMapper);
    }

    @Test
    void createOrEditBasicData_propagatesIOException() throws Exception {
        File target = tempDir.resolve("basic-data.json").toFile();
        var service = serviceFor(target);
        var dto = new BasicDataDTO();

        doThrow(new IOException("disk full")).when(objectMapper).writeValue(target, dto);

        assertThrows(IOException.class, () -> service.createOrEditBasicData(dto));
    }

    @Test
    void getBasicData_readsAndReturnsDto() throws Exception {
        File target = tempDir.resolve("basic-data.json").toFile();
        var service = serviceFor(target);

        var expected = new BasicDataDTO();
        expected.setIssuedDate(new Date());

        when(objectMapper.readValue(target, BasicDataDTO.class)).thenReturn(expected);

        var result = service.getBasicData();

        assertSame(expected, result);
        verify(objectMapper).readValue(target, BasicDataDTO.class);
        verifyNoMoreInteractions(objectMapper);
    }

    @Test
    void getBasicData_propagatesIOException() throws Exception {
        File target = tempDir.resolve("basic-data.json").toFile();
        var service = serviceFor(target);

        when(objectMapper.readValue(target, BasicDataDTO.class))
                .thenThrow(new IOException("not found"));

        assertThrows(IOException.class, service::getBasicData);
    }
}
