package cz.kavka.service;

import cz.kavka.dto.SchoolFeeDTO;
import cz.kavka.dto.mapper.SchoolFeeMapper;
import cz.kavka.entity.SchoolFeeEntity;
import cz.kavka.entity.repository.SchoolFeeRepository;
import cz.kavka.service.serviceinterface.TitleAndContentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolFeeService implements TitleAndContentService<SchoolFeeDTO> {

    private final SchoolFeeMapper schoolFeeMapper;

    private final SchoolFeeRepository schoolFeeRepository;

    @Autowired
    public SchoolFeeService(SchoolFeeMapper schoolFeeMapper, SchoolFeeRepository schoolFeeRepository) {
        this.schoolFeeMapper = schoolFeeMapper;
        this.schoolFeeRepository = schoolFeeRepository;
    }

    @Override
    public SchoolFeeDTO createOrEdit(SchoolFeeDTO schoolFeeDTO) {
        SchoolFeeEntity savedEntity = schoolFeeRepository.save(schoolFeeMapper.toEntity(schoolFeeDTO));
        return schoolFeeMapper.toDTO(savedEntity);
    }

    @Override
    public SchoolFeeDTO get() {
        return schoolFeeMapper.toDTO(schoolFeeRepository.findById(1L).orElseThrow(EntityNotFoundException::new));
    }

}
