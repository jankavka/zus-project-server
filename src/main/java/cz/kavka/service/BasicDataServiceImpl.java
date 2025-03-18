package cz.kavka.service;

import cz.kavka.dto.BasicDataDTO;
import cz.kavka.dto.mapper.BasicDataMapper;
import cz.kavka.entity.BasicDataEntity;
import cz.kavka.entity.repository.BasicDataRepository;
import cz.kavka.service.serviceinterface.BasicDataService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasicDataServiceImpl implements BasicDataService {

    private final BasicDataRepository basicDataRepository;

    private final BasicDataMapper basicDataMapper;

    @Autowired
    public BasicDataServiceImpl(BasicDataRepository basicDataRepository, BasicDataMapper basicDataMapper ) {
        this.basicDataRepository = basicDataRepository;
        this.basicDataMapper = basicDataMapper;
    }

    @Override
    public BasicDataDTO createOrEdit(BasicDataDTO basicDataDTO) {
        BasicDataEntity savedEntity = basicDataRepository.save(basicDataMapper.toEntity(basicDataDTO));
        return basicDataMapper.toDTO(savedEntity);
    }

    @Override
    public BasicDataDTO get(BasicDataDTO basicDataDTO) {
        return basicDataMapper.toDTO(basicDataRepository.findById(1L).orElseThrow(EntityNotFoundException::new));
    }

}
