package cz.kavka.service.serviceinterface;

import cz.kavka.dto.BasicDataDTO;


public interface BasicDataService {

    /**
     * Creates or edits a record in database. Main principle is that there will be always one record in specific
     *      * database table (Long id = 1L)
     * @param basicDataDTO An DTO object with data of new or edited object
     * @return DTO representation of a new object
     */
    BasicDataDTO createOrEdit (BasicDataDTO basicDataDTO);

    /**
     * Fetches object form database
     * @return DTO representation of an object
     */
    BasicDataDTO get ();


}
