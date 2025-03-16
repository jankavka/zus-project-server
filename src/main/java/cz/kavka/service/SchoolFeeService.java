package cz.kavka.service;

import cz.kavka.dto.SchoolFeeDTO;
import cz.kavka.dto.mapper.SchoolFeeMapper;
import cz.kavka.entity.SchoolFeeEntity;
import cz.kavka.entity.repository.SchoolFeeRepository;
import cz.kavka.service.serviceInterface.NameAndContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolFeeService implements NameAndContentService<SchoolFeeDTO> {

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

}
