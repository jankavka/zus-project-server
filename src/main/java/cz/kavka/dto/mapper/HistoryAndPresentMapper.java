package cz.kavka.dto.mapper;

import cz.kavka.dto.HistoryAndPresentDTO;
import cz.kavka.entity.HistoryAndPresentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryAndPresentMapper {

    HistoryAndPresentEntity toEntity(HistoryAndPresentDTO historyAndPresentDTO);

    HistoryAndPresentDTO toDTO(HistoryAndPresentEntity historyAndPresentEntity);
}
