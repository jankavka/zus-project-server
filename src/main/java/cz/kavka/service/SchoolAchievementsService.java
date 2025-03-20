package cz.kavka.service;

import cz.kavka.dto.SchoolAchievementsDTO;
import cz.kavka.dto.mapper.SchoolAchievementsMapper;
import cz.kavka.entity.SchoolAchievementsEntity;
import cz.kavka.entity.repository.SchoolAchievementsRepository;
import cz.kavka.service.serviceinterface.TitleAndContentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolAchievementsService implements TitleAndContentService<SchoolAchievementsDTO> {

    private final SchoolAchievementsRepository schoolAchievementsRepository;

    private SchoolAchievementsMapper schoolAchievementsMapper;

    @Autowired
    public SchoolAchievementsService(SchoolAchievementsRepository schoolAchievementsRepository, SchoolAchievementsMapper schoolAchievementsMapper){
        this.schoolAchievementsMapper = schoolAchievementsMapper;
        this.schoolAchievementsRepository = schoolAchievementsRepository;
    }

    @Override
    public SchoolAchievementsDTO createOrEdit(SchoolAchievementsDTO schoolAchievementsDTO) {
        SchoolAchievementsEntity savedEntity = schoolAchievementsRepository.save(schoolAchievementsMapper.toEntity(schoolAchievementsDTO));
        return schoolAchievementsMapper.toDTO(savedEntity);
    }

    @Override
    public SchoolAchievementsDTO get() {
        return schoolAchievementsMapper.toDTO(schoolAchievementsRepository.findById(1L).orElseThrow(EntityNotFoundException::new));
    }

}
