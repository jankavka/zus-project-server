package cz.kavka.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kavka.dto.EntranceExamDTO;
import cz.kavka.service.serviceinterface.EntranceExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class EntranceExamServiceImpl implements EntranceExamService {
    private final String filePath;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Object writeLock = new Object();


    @Autowired
    public EntranceExamServiceImpl(@Value("${entrance-exam.file-path:data/entrance-exam.json}") String filePath) {
        this.filePath = filePath;

    }


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
