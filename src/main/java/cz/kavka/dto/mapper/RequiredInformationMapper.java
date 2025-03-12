package cz.kavka.dto.mapper;

import cz.kavka.dto.RequiredInformationDTO;
import cz.kavka.entity.RequiredInformationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequiredInformationMapper {

    RequiredInformationDTO toDTO (RequiredInformationEntity requiredInformationEntity);

    RequiredInformationEntity toEntity (RequiredInformationDTO requiredInformationDTO);
}
