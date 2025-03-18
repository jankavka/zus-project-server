package cz.kavka.service.serviceinterface;

import cz.kavka.dto.BasicDataDTO;

public interface BasicDataService {

    BasicDataDTO createOrEdit (BasicDataDTO basicDataDTO);

    BasicDataDTO get (BasicDataDTO basicDataDTO);


}
