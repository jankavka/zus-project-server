package cz.kavka.service;

import cz.kavka.dto.RequiredInformationDTO;
import cz.kavka.dto.mapper.RequiredInformationMapper;
import cz.kavka.entity.RequiredInformationEntity;
import cz.kavka.entity.repository.RequiredInformationRepository;
import cz.kavka.service.serviceinterface.RequiredInformationService;
import jakarta.persistence.EntityNotFoundException;
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
    public RequiredInformationDTO createOrEdit(RequiredInformationDTO requiredInformationDTO) {
        RequiredInformationEntity savedEntity = requiredInformationRepository.save(requiredInformationMapper.toEntity(requiredInformationDTO));
        return requiredInformationMapper.toDTO(savedEntity);
    }

    @Override
    public RequiredInformationDTO get(RequiredInformationDTO requiredInformationDTO) {
        return requiredInformationMapper.toDTO(requiredInformationRepository.findById(1L).orElseThrow(EntityNotFoundException::new));
    }
}
