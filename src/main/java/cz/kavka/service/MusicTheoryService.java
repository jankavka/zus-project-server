package cz.kavka.service;

import cz.kavka.dto.MusicTheoryDTO;
import cz.kavka.dto.mapper.MusicTheoryMapper;
import cz.kavka.entity.MusicTheoryEntity;
import cz.kavka.entity.repository.MusicTheoryRepository;
import cz.kavka.service.serviceinterface.TitleAndContentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicTheoryService implements TitleAndContentService<MusicTheoryDTO> {

    private final MusicTheoryMapper musicTheoryMapper;

    private final MusicTheoryRepository musicTheoryRepository;

    @Autowired
    public MusicTheoryService(MusicTheoryMapper musicTheoryMapper, MusicTheoryRepository musicTheoryRepository){
        this.musicTheoryMapper = musicTheoryMapper;
        this.musicTheoryRepository = musicTheoryRepository;
    }

    @Override
    public MusicTheoryDTO createOrEdit(MusicTheoryDTO musicTheoryDTO) {
        MusicTheoryEntity savedEntity = musicTheoryRepository.save(musicTheoryMapper.toEntity(musicTheoryDTO));
        return musicTheoryMapper.toDTO(savedEntity);
    }

    @Override
    public MusicTheoryDTO get() {
        return musicTheoryMapper.toDTO(musicTheoryRepository.findById(1L).orElseThrow(EntityNotFoundException::new));
    }

}
