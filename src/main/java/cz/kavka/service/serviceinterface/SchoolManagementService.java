package cz.kavka.service.serviceinterface;

import cz.kavka.dto.SchoolManagementDTO;

import java.util.List;

public interface SchoolManagementService {

    SchoolManagementDTO createMember(SchoolManagementDTO schoolManagementDTO);

    SchoolManagementDTO editMember(SchoolManagementDTO schoolManagementDTO, Long id);

    SchoolManagementDTO getMember(Long id);

    List<SchoolManagementDTO> getAll();

    SchoolManagementDTO deleteMember(Long id);
}
