package cz.kavka.service;

import cz.kavka.dto.SchoolAchievementsDTO;
import cz.kavka.dto.mapper.SchoolAchievementsMapper;
import cz.kavka.entity.SchoolAchievementsEntity;
import cz.kavka.entity.repository.SchoolAchievementsRepository;
import cz.kavka.entity.repository.SchoolYearRepository;
import cz.kavka.service.serviceinterface.SchoolAchievementsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SchoolAchievementsServiceImpl implements SchoolAchievementsService {

    private final SchoolAchievementsRepository schoolAchievementsRepository;

    private final SchoolAchievementsMapper schoolAchievementsMapper;

    private final SchoolYearRepository schoolYearRepository;



    @Transactional
    @Override
    public SchoolAchievementsDTO createAchievement(SchoolAchievementsDTO schoolAchievementsDTO) {
        if (schoolAchievementsDTO.getSchoolYear() == null ||
                schoolAchievementsDTO.getContent() == null ||
                schoolAchievementsDTO.getTitle() == null) {
            throw new IllegalArgumentException("Error: entity must not by null");
        }
        SchoolAchievementsEntity entityToSave = schoolAchievementsMapper.toEntity(schoolAchievementsDTO);
        entityToSave.setSchoolYear(schoolYearRepository.getReferenceById(schoolAchievementsDTO.getSchoolYear().getId()));
        SchoolAchievementsEntity savedEntity = schoolAchievementsRepository.save(entityToSave);

        return schoolAchievementsMapper.toDTO(savedEntity);
    }

    @Transactional
    @Override
    public SchoolAchievementsDTO updateAchievement(Long id, SchoolAchievementsDTO schoolAchievementsDTO) {
        if (id == null) {
            throw new IllegalArgumentException("Error: Id doesn't exist");
        }
        if (schoolAchievementsRepository.existsById(id)) {
            schoolAchievementsDTO.setId(id);
            SchoolAchievementsEntity editedEntity = schoolAchievementsRepository.save(schoolAchievementsMapper.toEntity(schoolAchievementsDTO));
            return schoolAchievementsMapper.toDTO(editedEntity);
        } else {
            throw new EntityNotFoundException("Error: Entity not found");
        }
    }

    @Override
    public SchoolAchievementsDTO getAchievement(Long id) {
        return schoolAchievementsMapper.toDTO(schoolAchievementsRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<SchoolAchievementsDTO> getAllAchievements() {
        return schoolAchievementsRepository.findAll().stream().map(schoolAchievementsMapper::toDTO).toList();
    }

    @Transactional
    @Override
    public SchoolAchievementsDTO deleteAchievement(Long id) {
        if (schoolAchievementsRepository.existsById(id)) {


            SchoolAchievementsEntity schoolAchievementsEntity = schoolAchievementsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            SchoolAchievementsDTO deletedAchievement = schoolAchievementsMapper.toDTO(schoolAchievementsEntity);
            schoolAchievementsRepository.delete(schoolAchievementsEntity);

            return deletedAchievement;
        } else {
            throw new EntityNotFoundException("Error: Entity not found");
        }

    }

    @Override
    public List<SchoolAchievementsDTO> getAchievementsByYear(Long yearId) {

        return schoolAchievementsRepository.getAllAchievementsByYear(yearId)
                .stream()
                .map(schoolAchievementsMapper::toDTO)
                .toList();

    }
}
