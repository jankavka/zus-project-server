package cz.kavka.service.serviceinterface;

import cz.kavka.dto.TeachersDTO;

public interface TeachersService {

    TeachersDTO create (TeachersDTO teachersDTO);

    TeachersDTO edit (TeachersDTO teachersDTO, Long id);

    TeachersDTO get (TeachersDTO teachersDTO, Long id);
}
