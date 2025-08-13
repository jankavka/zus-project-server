package cz.kavka.service.serviceinterface;

import cz.kavka.dto.BasicDataDTO;

import java.io.IOException;
import java.net.URISyntaxException;


public interface BasicDataService {

    /**
     * Method for getting BasicData from JSON file
     * @return a dto representation of BasicData
     * @throws IOException while an error during file operation occurs
     */
    BasicDataDTO getBasicData() throws IOException;

    /**
     * Method which saves provided data to JSON file
     * @param basicDataDTO provided data
     * @return a DTO representation of saved data
     * @throws IOException while an error during file operation occurs
     */
    BasicDataDTO createOrEditBasicData(BasicDataDTO basicDataDTO) throws IOException;


}
