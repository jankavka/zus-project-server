package cz.kavka.dto.mapper;

import cz.kavka.dto.GroupTrainingScheduleDTO;
import cz.kavka.entity.GroupTrainingScheduleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupTrainingScheduleMapper {

    GroupTrainingScheduleDTO toDTO(GroupTrainingScheduleEntity groupTrainingScheduleEntity);

    GroupTrainingScheduleEntity toEntity(GroupTrainingScheduleDTO groupTrainingScheduleDTO);
}
