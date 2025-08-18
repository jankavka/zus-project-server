package cz.kavka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kavka.dto.RequiredInformationDTO;
import cz.kavka.service.serviceinterface.RequiredInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class RequiredInformationServiceImpl implements RequiredInformationService {

    //Instance of file loaded form exact path
    private final File file;

    //Object for operations with JSON file
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Object writeLock = new Object();

    @Autowired
    public RequiredInformationServiceImpl(
            @Value("${requiredinformation.file-path:data/required-information.json}") String filePath) {
        this.file = new File(filePath);
    }

    /**
     * Method that creates a data representation in JSON file
     *
     * @param requiredInformationDTO provided data
     * @return an object with saved data
     * @throws IOException while an error during file operation occurs
     */
    @Override
    public RequiredInformationDTO createOrEdit(RequiredInformationDTO requiredInformationDTO) throws IOException {
        requiredInformationDTO.setIssuedDate(new Date());
        synchronized (writeLock) {
            objectMapper.writeValue(file, requiredInformationDTO);
            return requiredInformationDTO;
        }

    }

    /**
     * Method that loads data from JSON file
     *
     * @return a DTO representation of data from JSON file
     * @throws IOException while an error during file operation occurs
     */
    @Override
    public RequiredInformationDTO getInfo() throws IOException {
        synchronized (writeLock) {
            return objectMapper.readValue(file, RequiredInformationDTO.class);
        }

    }


}
