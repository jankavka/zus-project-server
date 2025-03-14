package cz.kavka.service;

import cz.kavka.dto.RequiredInformationDTO;
import cz.kavka.dto.mapper.RequiredInformationMapper;
import cz.kavka.entity.RequiredInformationEntity;
import cz.kavka.entity.repository.RequiredInformationRepository;
import cz.kavka.service.serviceInterface.RequiredInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequiredInformationServiceImpl implements RequiredInformationService {

    private final RequiredInformationRepository requiredInformationRepository;

    private final RequiredInformationMapper requiredInformationMapper;

    @Autowired
    public RequiredInformationServiceImpl(RequiredInformationRepository requiredInformationRepository, RequiredInformationMapper requiredInformationMapper){
        this.requiredInformationMapper = requiredInformationMapper;
        this.requiredInformationRepository = requiredInformationRepository;
    }

    @Override
    public RequiredInformationDTO create(RequiredInformationDTO requiredInformationDTO) {
        requiredInformationDTO.setId(1L);
        RequiredInformationEntity savedEntity = requiredInformationRepository.save(requiredInformationMapper.toEntity(requiredInformationDTO));
        return requiredInformationMapper.toDTO(savedEntity);
    }
}
