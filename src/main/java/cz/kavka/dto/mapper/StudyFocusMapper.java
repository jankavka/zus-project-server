package cz.kavka.dto.mapper;

import cz.kavka.dto.StudyFocusDTO;
import cz.kavka.entity.StudyFocusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudyFocusMapper {

    StudyFocusDTO toDTO (StudyFocusEntity studyFocusEntity);

    StudyFocusEntity toEntity (StudyFocusDTO studyFocusDTO);
}
