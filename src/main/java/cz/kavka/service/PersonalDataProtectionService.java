package cz.kavka.service;

import cz.kavka.dto.PersonalDataProtectionDTO;
import cz.kavka.dto.mapper.PersonalDataProtectionMapper;
import cz.kavka.entity.PersonalDataProtectionEntity;
import cz.kavka.entity.repository.PersonalDataProtectionRepository;
import cz.kavka.service.serviceInterface.NameAndContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalDataProtectionService implements NameAndContentService<PersonalDataProtectionDTO> {

    private final PersonalDataProtectionMapper personalDataProtectionMapper;

    private final PersonalDataProtectionRepository personalDataProtectionRepository;

    @Autowired
    public PersonalDataProtectionService(PersonalDataProtectionMapper personalDataProtectionMapper, PersonalDataProtectionRepository personalDataProtectionRepository){
        this.personalDataProtectionMapper = personalDataProtectionMapper;
        this.personalDataProtectionRepository = personalDataProtectionRepository;
    }

    @Override
    public PersonalDataProtectionDTO create(PersonalDataProtectionDTO personalDataProtectionDTO) {
        personalDataProtectionDTO.setId(1L);
        PersonalDataProtectionEntity savedEntity = personalDataProtectionRepository.save(personalDataProtectionMapper.toEntity(personalDataProtectionDTO));
        return personalDataProtectionMapper.toDTO(savedEntity);
    }
}
