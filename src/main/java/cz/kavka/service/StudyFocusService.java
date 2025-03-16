package cz.kavka.service;

import cz.kavka.dto.StudyFocusDTO;
import cz.kavka.dto.mapper.StudyFocusMapper;
import cz.kavka.entity.StudyFocusEntity;
import cz.kavka.entity.repository.StudyFocusRepository;
import cz.kavka.service.serviceInterface.NameAndContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudyFocusService implements NameAndContentService<StudyFocusDTO> {

    private final StudyFocusRepository studyFocusRepository;

    private final StudyFocusMapper studyFocusMapper;

    @Autowired
    public StudyFocusService(StudyFocusRepository studyFocusRepository, StudyFocusMapper studyFocusMapper) {
        this.studyFocusMapper = studyFocusMapper;
        this.studyFocusRepository = studyFocusRepository;
    }

    @Override
    public StudyFocusDTO createOrEdit(StudyFocusDTO studyFocusDTO) {
        StudyFocusEntity savedEntity = studyFocusRepository.save(studyFocusMapper.toEntity(studyFocusDTO));
        return studyFocusMapper.toDTO(savedEntity);
    }

}
