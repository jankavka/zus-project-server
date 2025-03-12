package cz.kavka.dto.mapper;

import cz.kavka.dto.PersonalDataProtectionDTO;
import cz.kavka.entity.PersonalDataProtectionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonalDataProtectionMapper {

    PersonalDataProtectionEntity toEntity(PersonalDataProtectionDTO personalDataProtectionDTO);

    PersonalDataProtectionDTO toDTO(PersonalDataProtectionEntity personalDataProtectionEntity);
}
