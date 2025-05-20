package cz.kavka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kavka.dto.RequiredInformationDTO;
import cz.kavka.service.serviceinterface.RequiredInformationService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class RequiredInformationServiceImpl implements RequiredInformationService {

    //String path to file which stores data related to RequiredInformation
    private final String filePath = "src/main/resources/required-information.json";

    //Instance of file loaded form exact path
    private final File file = new File(filePath);

    //Object for operations with JSON file
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Method that creates a data representation in JSON file
     * @param requiredInformationDTO provided data
     * @return an object with saved data
     * @throws IOException while an error during file operation occurs
     */
    @Override
    public RequiredInformationDTO createOrEdit(RequiredInformationDTO requiredInformationDTO) throws IOException {
        requiredInformationDTO.setIssuedDate(new Date());
        objectMapper.writeValue(file, requiredInformationDTO);
        return requiredInformationDTO;
    }

    /**
     * Method that loads data from JSON file
     * @return a DTO representation of data from JSON file
     * @throws IOException while an error during file operation occurs
     */
    @Override
    public RequiredInformationDTO getInfo() throws IOException {
        return objectMapper.readValue(file, RequiredInformationDTO.class);
    }


}
