package cz.kavka.service.serviceinterface;

import cz.kavka.dto.TeachersDTO;

import java.util.List;

public interface TeachersService {

    TeachersDTO create (TeachersDTO teachersDTO);

    TeachersDTO edit (TeachersDTO teachersDTO, Long id);

    TeachersDTO get (Long id);

    List<TeachersDTO> getAll();
}
