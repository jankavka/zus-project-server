package cz.kavka.service;

import cz.kavka.dto.SchoolManagementDTO;
import cz.kavka.dto.mapper.SchoolManagementMapper;
import cz.kavka.entity.SchoolManagementEntity;
import cz.kavka.entity.repository.SchoolManagementRepository;
import cz.kavka.service.serviceinterface.SchoolManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolManagementServiceImpl implements SchoolManagementService {

    private final SchoolManagementMapper schoolManagementMapper;

    private final SchoolManagementRepository schoolManagementRepository;

    @Autowired
    public SchoolManagementServiceImpl(SchoolManagementRepository schoolManagementRepository, SchoolManagementMapper schoolManagementMapper) {
        this.schoolManagementRepository = schoolManagementRepository;
        this.schoolManagementMapper = schoolManagementMapper;
    }

    @Override
    public SchoolManagementDTO createMember(SchoolManagementDTO schoolManagementDTO){
        SchoolManagementEntity savedEntity = schoolManagementRepository.save(schoolManagementMapper.toEntity(schoolManagementDTO));
        return schoolManagementMapper.toDTO(savedEntity);
    }

    @Override
    public SchoolManagementDTO editMember(SchoolManagementDTO schoolManagementDTO, Long id) {
        schoolManagementDTO.setId(id);
        SchoolManagementEntity editedEntity = schoolManagementRepository.save(schoolManagementMapper.toEntity(schoolManagementDTO));
        return schoolManagementMapper.toDTO(editedEntity);
    }

    @Override
    public SchoolManagementDTO getMember(Long id) {
        return schoolManagementMapper.toDTO(schoolManagementRepository.getReferenceById(id));
    }
}
