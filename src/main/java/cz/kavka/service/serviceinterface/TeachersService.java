package cz.kavka.service.serviceinterface;

import cz.kavka.dto.TeachersDTO;

import java.util.List;

public interface TeachersService {

    TeachersDTO createTeacher(TeachersDTO teachersDTO);

    TeachersDTO editTeacher (TeachersDTO teachersDTO, Long id);

    TeachersDTO getTeacher (Long id);

    List<TeachersDTO> getAll();

    TeachersDTO deleteTeacher(Long id);
}
