package cz.kavka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kavka.dto.BasicDataDTO;
import cz.kavka.service.serviceinterface.BasicDataService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import java.util.Date;

@Service
public class BasicDataServiceImpl implements BasicDataService {

    //Object for operations with JSON file
    private final ObjectMapper objectMapper;

    @Value("${basicdata.file-path:data/basic-data.json}")
    private String filePath;

    private final File file;

    private final Object writeLock = new Object();

    @Autowired
    public BasicDataServiceImpl(
            ObjectMapper objectMapper,
            @Value("${basicdata.file-path:data/basic-data.json}") String filePath) {
        this.objectMapper = objectMapper;
        this.file = new File(filePath);
    }


    /**
     * Method that creates a data representation in JSON file
     *
     * @param basicDataDTO provided data
     * @return a dto representation of saved data
     * @throws IOException while an error during file operation occurs
     */
    @Override
    public BasicDataDTO createOrEditBasicData(BasicDataDTO basicDataDTO) throws IOException {

        basicDataDTO.setIssuedDate(new Date());

        //later do Atomic moves
        synchronized (writeLock) {
            objectMapper.writeValue(file, basicDataDTO);
            return basicDataDTO;
        }

    }

    /**
     * Method that loads data from JSON file
     *
     * @return a DTO representation of BasicData
     * @throws IOException while an error during file operation occurs
     */
    @Override
    public BasicDataDTO getBasicData() throws IOException {

        //later do Atomic moves
        synchronized (writeLock) {
            return objectMapper.readValue(file, BasicDataDTO.class);
        }
    }


}
