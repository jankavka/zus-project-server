package cz.kavka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kavka.dto.BasicDataDTO;
import cz.kavka.service.serviceinterface.BasicDataService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class BasicDataServiceImpl implements BasicDataService {

    //String path to file which stores data related to BasicData
    private final String filePath = "src/main/resources/basic-data.json";

    //Object for operations with JSON file
    private final ObjectMapper objectMapper = new ObjectMapper();

    //Instance of file loaded form exact path
    private final File file = new File(filePath);

    /**
     * Method that creates a data representation in JSON file
     * @param basicDataDTO provided data
     * @return a dto representation of saved data
     * @throws IOException while an error during file operation occurs
     */
    @Override
    public BasicDataDTO createOrEditBasicData(BasicDataDTO basicDataDTO) throws IOException {
            objectMapper.writeValue(file, basicDataDTO);
            return basicDataDTO;
    }

    /**
     * Method that loads data from JSON file
     * @return a DTO representation of BasicData
     * @throws IOException while an error during file operation occurs
     */
    @Override
    public BasicDataDTO getBasicData() throws IOException {
        return objectMapper.readValue(file, BasicDataDTO.class);
    }


}
