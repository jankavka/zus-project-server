package cz.kavka.service;

import cz.kavka.dto.SchoolManagementDTO;
import cz.kavka.dto.mapper.SchoolManagementMapper;
import cz.kavka.entity.SchoolManagementEntity;
import cz.kavka.entity.repository.SchoolManagementRepository;
import cz.kavka.service.serviceinterface.SchoolManagementService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SchoolManagementServiceImpl implements SchoolManagementService {

    private final SchoolManagementMapper schoolManagementMapper;

    private final SchoolManagementRepository schoolManagementRepository;

    @Autowired
    public SchoolManagementServiceImpl(SchoolManagementRepository schoolManagementRepository, SchoolManagementMapper schoolManagementMapper) {
        this.schoolManagementRepository = schoolManagementRepository;
        this.schoolManagementMapper = schoolManagementMapper;
    }

    @Transactional
    @Override
    public SchoolManagementDTO createMember(SchoolManagementDTO schoolManagementDTO) {
        if (schoolManagementDTO == null) {
            throw new IllegalArgumentException("Error: Entity must not by null");
        }
        SchoolManagementEntity savedEntity = schoolManagementRepository.save(schoolManagementMapper.toEntity(schoolManagementDTO));
        return schoolManagementMapper.toDTO(savedEntity);
    }

    @Transactional
    @Override
    public SchoolManagementDTO editMember(SchoolManagementDTO schoolManagementDTO, Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Error: Id must not by null");
        }
        if (schoolManagementRepository.existsById(id)) {
            schoolManagementDTO.setId(id);
            SchoolManagementEntity editedEntity = schoolManagementRepository.save(schoolManagementMapper.toEntity(schoolManagementDTO));
            return schoolManagementMapper.toDTO(editedEntity);
        } else {
            throw new EntityNotFoundException("Error: Entity to edit not found");
        }
    }

    @Override
    public SchoolManagementDTO getMember(Long id) {
        return schoolManagementMapper.toDTO(schoolManagementRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<SchoolManagementDTO> getAll() {
        return schoolManagementRepository.findAll().stream().map(schoolManagementMapper::toDTO).toList();
    }

    @Transactional
    @Override
    public SchoolManagementDTO deleteMember(Long id) {
        if (schoolManagementRepository.existsById(id)) {
            SchoolManagementEntity entityToDelete = schoolManagementRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            schoolManagementRepository.delete(entityToDelete);
            return schoolManagementMapper.toDTO(entityToDelete);
        } else {
            throw new EntityNotFoundException("Error: Entity to delete not found");
        }

    }
}
