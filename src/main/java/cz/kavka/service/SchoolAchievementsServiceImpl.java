package cz.kavka.service;

import cz.kavka.dto.SchoolAchievementsDTO;
import cz.kavka.dto.mapper.SchoolAchievementsMapper;
import cz.kavka.entity.SchoolAchievementsEntity;
import cz.kavka.entity.repository.SchoolAchievementsRepository;
import cz.kavka.entity.repository.SchoolYearRepository;
import cz.kavka.service.serviceinterface.SchoolAchievementsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolAchievementsServiceImpl implements SchoolAchievementsService {

    private final SchoolAchievementsRepository schoolAchievementsRepository;

    private final SchoolAchievementsMapper schoolAchievementsMapper;

    private final SchoolYearRepository schoolYearRepository;

    @Autowired
    public SchoolAchievementsServiceImpl(SchoolAchievementsRepository schoolAchievementsRepository, SchoolAchievementsMapper schoolAchievementsMapper, SchoolYearRepository schoolYearRepository) {
        this.schoolAchievementsMapper = schoolAchievementsMapper;
        this.schoolAchievementsRepository = schoolAchievementsRepository;
        this.schoolYearRepository = schoolYearRepository;
    }


    @Override
    public SchoolAchievementsDTO createAchievement(SchoolAchievementsDTO schoolAchievementsDTO) {
        SchoolAchievementsEntity entityToSave = schoolAchievementsMapper.toEntity(schoolAchievementsDTO);
        entityToSave.setSchoolYear(schoolYearRepository.getReferenceById(schoolAchievementsDTO.getSchoolYear().getId()));
        schoolAchievementsRepository.save(entityToSave);

        return schoolAchievementsMapper.toDTO(entityToSave);
    }

    @Override
    public SchoolAchievementsDTO updateAchievement(Long id, SchoolAchievementsDTO schoolAchievementsDTO) {
        schoolAchievementsDTO.setId(id);
        SchoolAchievementsEntity editedEntity = schoolAchievementsRepository.save(schoolAchievementsMapper.toEntity(schoolAchievementsDTO));
        return schoolAchievementsMapper.toDTO(editedEntity);
    }

    @Override
    public SchoolAchievementsDTO getAchievement(Long id) {
        return schoolAchievementsMapper.toDTO(schoolAchievementsRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<SchoolAchievementsDTO> getAllAchievements() {
        return schoolAchievementsRepository.findAll().stream().map(schoolAchievementsMapper::toDTO).toList();
    }

    @Override
    public SchoolAchievementsDTO deleteAchievement(Long id) {
        SchoolAchievementsEntity schoolAchievementsEntity = schoolAchievementsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        SchoolAchievementsDTO deletedAchievement = schoolAchievementsMapper.toDTO(schoolAchievementsEntity);
        schoolAchievementsRepository.delete(schoolAchievementsEntity);

        return deletedAchievement;

    }
}
