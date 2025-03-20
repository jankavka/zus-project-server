package cz.kavka.service.serviceinterface;

import cz.kavka.dto.RequiredInformationDTO;

public interface RequiredInformationService {

    RequiredInformationDTO createOrEdit(RequiredInformationDTO requiredInformationDTO);

    RequiredInformationDTO get();

}
