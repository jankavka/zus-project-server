package cz.kavka.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kavka.dto.TitleAndContentDTO;
import cz.kavka.service.serviceinterface.TitleAndContentService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class TitleAndContentServiceImpl implements TitleAndContentService {

    private final String FILE_PATH = "src/main/resources/title-and-content.json";
    private final ObjectMapper objectMapper= new ObjectMapper();

    /**
     * Reads whole content from json file.
     * @return JSON with all TitleAndContent type records
     * @throws IOException while an error during file operation occurs
     */
    @Override
    public Map<String, TitleAndContentDTO> getContent() throws IOException{
        File file = new File(FILE_PATH);

        return objectMapper.readValue(file, new TypeReference<>() {});
    }

    /**
     * Gets concrete section from JSON represented by key
     * @param key representation of sub object in JSON
     * @return object of TitleAndContentDTO
     * @throws IOException while an error during file operation occurs
     */
    @Override
    public Optional<TitleAndContentDTO> getSection(String key) throws IOException{
        return Optional.ofNullable(getContent().get(key));
    }


    /**
     * Updated or creates a new object of TitleAndContent in JSON file
     * @param key is String value of sub object in JSON file
     * @param titleAndContentDTO as an object connected with key in JSON file
     * @return updated JSON file
     * @throws IOException while an error during file operation occurs
     */
    @Override
    public Map<String, TitleAndContentDTO> updateContent(String key, TitleAndContentDTO titleAndContentDTO) throws IOException {
        File file = new File(FILE_PATH);
        titleAndContentDTO.setIssuedDate(new Date());

        Map<String, TitleAndContentDTO> content = getContent();
        content.put(key, titleAndContentDTO);

        objectMapper.writeValue(file, content);

        return content;
    }
}
