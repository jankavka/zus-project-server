package cz.kavka.dto.mapper;

import cz.kavka.dto.MusicTheoryDTO;
import cz.kavka.entity.MusicTheoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MusicTheoryMapper {

    MusicTheoryDTO toDTO (MusicTheoryEntity musicTheoryEntity);

    MusicTheoryEntity toEntity(MusicTheoryDTO musicTheoryDTO);
}
