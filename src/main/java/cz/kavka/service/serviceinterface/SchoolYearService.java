package cz.kavka.service.serviceinterface;

import cz.kavka.dto.SchoolYearDTO;

import java.util.List;

public interface SchoolYearService {

    SchoolYearDTO create(SchoolYearDTO schoolYearDTO);

    SchoolYearDTO edit(SchoolYearDTO schoolYearDTO, Long id);

    SchoolYearDTO get(Long id);

    List<SchoolYearDTO> getAll();

    SchoolYearDTO delete(Long id);
}
