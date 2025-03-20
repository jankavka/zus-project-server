package cz.kavka.service;

import cz.kavka.dto.PersonalDataProtectionDTO;
import cz.kavka.dto.mapper.PersonalDataProtectionMapper;
import cz.kavka.entity.PersonalDataProtectionEntity;
import cz.kavka.entity.repository.PersonalDataProtectionRepository;
import cz.kavka.service.serviceinterface.TitleAndContentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalDataProtectionService implements TitleAndContentService<PersonalDataProtectionDTO> {

    private final PersonalDataProtectionMapper personalDataProtectionMapper;

    private final PersonalDataProtectionRepository personalDataProtectionRepository;

    @Autowired
    public PersonalDataProtectionService(PersonalDataProtectionMapper personalDataProtectionMapper, PersonalDataProtectionRepository personalDataProtectionRepository){
        this.personalDataProtectionMapper = personalDataProtectionMapper;
        this.personalDataProtectionRepository = personalDataProtectionRepository;
    }

    @Override
    public PersonalDataProtectionDTO createOrEdit(PersonalDataProtectionDTO personalDataProtectionDTO) {
        PersonalDataProtectionEntity savedEntity = personalDataProtectionRepository.save(personalDataProtectionMapper.toEntity(personalDataProtectionDTO));
        return personalDataProtectionMapper.toDTO(savedEntity);
    }

    @Override
    public PersonalDataProtectionDTO get() {
        return personalDataProtectionMapper.toDTO(personalDataProtectionRepository.findById(1L).orElseThrow(EntityNotFoundException::new));
    }

}
