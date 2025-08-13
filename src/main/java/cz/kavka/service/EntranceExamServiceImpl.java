package cz.kavka.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kavka.dto.EntranceExamDTO;
import cz.kavka.service.serviceinterface.EntranceExamService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class EntranceExamServiceImpl implements EntranceExamService {
    private final String filePath = "data/entrance-exam.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Object writeLock = new Object();


    @Override
    public EntranceExamDTO updateEntranceExam(EntranceExamDTO entranceExamDTO) throws IOException {
        File file = new File(filePath);
        entranceExamDTO.setIssuedDate(new Date());
        synchronized (writeLock) {
            objectMapper.writeValue(file, entranceExamDTO);
            return entranceExamDTO;
        }


    }

    @Override
    public EntranceExamDTO getEntranceExam() throws IOException {
        File file = new File(filePath);

        synchronized (writeLock) {
            return objectMapper.readValue(file, new TypeReference<>() {
            });
        }

    }

    @Override
    public boolean isHidden() throws IOException {
        synchronized (writeLock) {
            return getEntranceExam().isHidden();
        }

    }
}
