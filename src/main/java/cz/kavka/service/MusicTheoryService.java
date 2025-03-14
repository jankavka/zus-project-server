package cz.kavka.service;

import cz.kavka.dto.MusicTheoryDTO;
import cz.kavka.dto.mapper.MusicTheoryMapper;
import cz.kavka.entity.MusicTheoryEntity;
import cz.kavka.entity.repository.MusicTheoryRepository;
import cz.kavka.service.serviceInterface.NameAndContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicTheoryService implements NameAndContentService<MusicTheoryDTO> {

    private MusicTheoryMapper musicTheoryMapper;

    private MusicTheoryRepository musicTheoryRepository;

    @Autowired
    public MusicTheoryService(MusicTheoryMapper musicTheoryMapper, MusicTheoryRepository musicTheoryRepository){
        this.musicTheoryMapper = musicTheoryMapper;
        this.musicTheoryRepository = musicTheoryRepository;
    }

    @Override
    public MusicTheoryDTO create(MusicTheoryDTO musicTheoryDTO) {
        musicTheoryDTO.setId(1L);
        MusicTheoryEntity savedEntity = musicTheoryRepository.save(musicTheoryMapper.toEntity(musicTheoryDTO));
        return musicTheoryMapper.toDTO(savedEntity);
    }
}
