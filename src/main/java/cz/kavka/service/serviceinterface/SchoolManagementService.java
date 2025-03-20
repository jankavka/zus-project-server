package cz.kavka.service.serviceinterface;

import cz.kavka.dto.SchoolManagementDTO;

public interface SchoolManagementService {

    SchoolManagementDTO createMember(SchoolManagementDTO schoolManagementDTO);

    SchoolManagementDTO editMember(SchoolManagementDTO schoolManagementDTO, Long id);

    SchoolManagementDTO getMember(Long id);
}
