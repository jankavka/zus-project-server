package cz.kavka.service.serviceinterface;

import cz.kavka.dto.SchoolManagementDTO;

public interface SchoolManagementService {

    SchoolManagementDTO create(SchoolManagementDTO schoolManagementDTO);

    SchoolManagementDTO edit (SchoolManagementDTO schoolManagementDTO, Long id);

    SchoolManagementDTO get(SchoolManagementDTO schoolManagementDTO, Long id);
}
