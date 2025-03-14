package cz.kavka.dto.mapper;

import cz.kavka.dto.SchoolFeeDTO;
import cz.kavka.entity.SchoolFeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolFeeMapper {

    SchoolFeeEntity toEntity(SchoolFeeDTO schoolFeeDTO);

    SchoolFeeDTO toDTO (SchoolFeeEntity schoolFeeEntity);
}
