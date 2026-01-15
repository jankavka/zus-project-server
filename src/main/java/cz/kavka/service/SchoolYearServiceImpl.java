package cz.kavka.service;

import cz.kavka.dto.SchoolYearDTO;
import cz.kavka.dto.mapper.SchoolYearMapper;
import cz.kavka.entity.SchoolYearEntity;
import cz.kavka.entity.repository.SchoolYearRepository;
import cz.kavka.service.serviceinterface.SchoolYearService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SchoolYearServiceImpl implements SchoolYearService {

    private final SchoolYearMapper schoolYearMapper;

    private final SchoolYearRepository schoolYearRepository;


    @Transactional
    @Override
    public SchoolYearDTO create(SchoolYearDTO schoolYearDTO) {
        if (schoolYearDTO == null) {
            throw new IllegalArgumentException("Error: Entity can not be null");
        }
        SchoolYearEntity savedEntity = schoolYearRepository.save(schoolYearMapper.toEntity(schoolYearDTO));
        return schoolYearMapper.toDTO(savedEntity);
    }

    @Transactional
    @Override
    public SchoolYearDTO edit(SchoolYearDTO schoolYearDTO, Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Error: Id can not be null");
        }
        if (schoolYearDTO == null) {
            throw new IllegalArgumentException("Error: Entity can not be null");
        }
        if (schoolYearRepository.existsById(id)) {
            schoolYearDTO.setId(id);
            SchoolYearEntity editedEntity = schoolYearRepository.save(schoolYearMapper.toEntity(schoolYearDTO));
            return schoolYearMapper.toDTO(editedEntity);
        } else {
            throw new EntityNotFoundException("Error: Entity to update not found");
        }
    }

    @Override
    public SchoolYearDTO get(Long id) {
        return schoolYearMapper.toDTO(schoolYearRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<SchoolYearDTO> getAll() {
        return schoolYearRepository.findAll().stream().map(schoolYearMapper::toDTO).toList();
    }

    @Transactional
    @Override
    public SchoolYearDTO delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Error: Id must not be null");
        }
        SchoolYearEntity entityToDelete = (schoolYearRepository.findById(id).orElseThrow(EntityNotFoundException::new));
        schoolYearRepository.delete(entityToDelete);
        return schoolYearMapper.toDTO(entityToDelete);
    }

}
