package cz.kavka.service.serviceinterface;

import cz.kavka.dto.RequiredInformationDTO;

import java.io.IOException;

public interface RequiredInformationService {

    /**
     * Method which saves provided data to JSON file
     * @param requiredInformationDTO provided data
     * @return a dto representation of saved data
     * @throws IOException while an error during file operation occurs
     */
    RequiredInformationDTO createOrEdit(RequiredInformationDTO requiredInformationDTO) throws IOException;

    /**
     * Method for getting data from JSON file
     * @return a dto representation of data in JSON file
     * @throws IOException while an error during file operation occurs
     */
    RequiredInformationDTO getInfo() throws IOException;

}
