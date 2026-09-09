package cz.kavka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kavka.dto.SchoolYearLinkDTO;
import cz.kavka.service.serviceinterface.SchoolYearLinkService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class SchoolYearLinkServiceImpl implements SchoolYearLinkService {

    private final String filePath;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Object writeLock = new Object();

    public SchoolYearLinkServiceImpl(
            @Value("${school-year-link.file-path:data/school-year-link.json}") String filePath) {
        this.filePath = filePath;
    }

    @Override
    public SchoolYearLinkDTO get() throws IOException {
        File file = new File(filePath);
        synchronized (writeLock) {
            // A missing file just means "not configured yet" -> empty URL,
            // which the frontend treats as "hide the menu item".
            if (!file.exists()) {
                return new SchoolYearLinkDTO("");
            }
            return objectMapper.readValue(file, SchoolYearLinkDTO.class);
        }
    }

    @Override
    public SchoolYearLinkDTO update(SchoolYearLinkDTO schoolYearLinkDTO) throws IOException {
        String url = schoolYearLinkDTO.getUrl() == null ? "" : schoolYearLinkDTO.getUrl().trim();
        SchoolYearLinkDTO normalized = new SchoolYearLinkDTO(url);
        File file = new File(filePath);
        synchronized (writeLock) {
            objectMapper.writeValue(file, normalized);
            return normalized;
        }
    }
}
