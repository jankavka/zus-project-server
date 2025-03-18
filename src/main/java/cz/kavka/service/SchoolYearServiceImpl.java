package cz.kavka.service;

import cz.kavka.dto.SchoolYearDTO;
import cz.kavka.dto.mapper.SchoolYearMapper;
import cz.kavka.entity.SchoolYearEntity;
import cz.kavka.entity.repository.SchoolYearRepository;
import cz.kavka.service.serviceinterface.SchoolYearService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolYearServiceImpl implements SchoolYearService {

    private SchoolYearMapper schoolYearMapper;

    private SchoolYearRepository schoolYearRepository;

    @Autowired
    public SchoolYearServiceImpl(SchoolYearMapper schoolYearMapper, SchoolYearRepository schoolYearRepository) {
        this.schoolYearMapper = schoolYearMapper;
        this.schoolYearRepository = schoolYearRepository;
    }

    @Override
    public SchoolYearDTO create(SchoolYearDTO schoolYearDTO) {
        SchoolYearEntity savedEntity = schoolYearRepository.save(schoolYearMapper.toEntity(schoolYearDTO));
        return schoolYearMapper.toDTO(savedEntity);
    }

    @Override
    public SchoolYearDTO edit(SchoolYearDTO schoolYearDTO, Long id) {
        schoolYearDTO.setId(id);
        SchoolYearEntity editedEntity = schoolYearRepository.save(schoolYearMapper.toEntity(schoolYearDTO));
        return schoolYearMapper.toDTO(editedEntity);
    }

    @Override
    public SchoolYearDTO get(Long id) {
        SchoolYearDTO schoolYearDTO;
        try {
            schoolYearDTO = schoolYearMapper.toDTO(schoolYearRepository.getReferenceById(id));
        } catch (RuntimeException e) {
            throw new EntityNotFoundException();
        }
        return schoolYearDTO;
    }

    @Override
    public List<SchoolYearDTO> getAll() {
        return schoolYearRepository.findAll().stream().map(schoolYearMapper::toDTO).toList();
    }

    @Override
    public SchoolYearDTO delete(Long id) {
        SchoolYearDTO schoolYearDTO;
        try{
            schoolYearDTO = schoolYearMapper.toDTO(schoolYearRepository.getReferenceById(id));
            schoolYearRepository.deleteById(id);
        }catch(RuntimeException e){
            throw new EntityNotFoundException();
        }
        return schoolYearDTO;
    }
}
