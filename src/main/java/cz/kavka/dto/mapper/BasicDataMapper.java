package cz.kavka.dto.mapper;

import cz.kavka.dto.BasicDataDTO;
import cz.kavka.entity.BasicDataEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BasicDataMapper {

    BasicDataDTO toDTO (BasicDataEntity basicDataEntity);

    BasicDataEntity toEntity (BasicDataDTO basicDataDTO);
}
