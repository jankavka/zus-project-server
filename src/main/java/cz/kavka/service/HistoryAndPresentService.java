package cz.kavka.service;

import cz.kavka.dto.HistoryAndPresentDTO;
import cz.kavka.dto.mapper.HistoryAndPresentMapper;
import cz.kavka.entity.HistoryAndPresentEntity;
import cz.kavka.entity.repository.HistoryAndPresentRepository;
import cz.kavka.service.serviceInterface.NameAndContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryAndPresentService implements NameAndContentService<HistoryAndPresentDTO> {

    private HistoryAndPresentMapper historyAndPresentMapper;

    private HistoryAndPresentRepository historyAndPresentRepository;

    @Autowired
    public HistoryAndPresentService(HistoryAndPresentRepository historyAndPresentRepository, HistoryAndPresentMapper historyAndPresentMapper){
        this.historyAndPresentMapper = historyAndPresentMapper;
        this.historyAndPresentRepository = historyAndPresentRepository;
    }

    @Override
    public HistoryAndPresentDTO create(HistoryAndPresentDTO historyAndPresentDTO) {
        HistoryAndPresentEntity savedEntity = historyAndPresentRepository.save(historyAndPresentMapper.toEntity(historyAndPresentDTO));
        return historyAndPresentMapper.toDTO(savedEntity);
    }
}
