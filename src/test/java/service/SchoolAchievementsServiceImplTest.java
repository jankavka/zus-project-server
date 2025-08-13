package service;

import cz.kavka.dto.SchoolAchievementsDTO;
import cz.kavka.dto.SchoolYearDTO;
import cz.kavka.dto.mapper.SchoolAchievementsMapper;
import cz.kavka.entity.SchoolAchievementsEntity;
import cz.kavka.entity.SchoolYearEntity;
import cz.kavka.entity.repository.SchoolAchievementsRepository;
import cz.kavka.entity.repository.SchoolYearRepository;
import cz.kavka.service.SchoolAchievementsServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchoolAchievementsServiceImplTest {

    @Mock SchoolAchievementsRepository achievementsRepo;
    @Mock SchoolAchievementsMapper achievementsMapper;
    @Mock SchoolYearRepository schoolYearRepo;

    @InjectMocks
    SchoolAchievementsServiceImpl service;

    // ---------- createAchievement ----------

    @Test
    void createAchievement_maps_setsSchoolYear_andSaves() {
        // input DTO with nested school year id
        var yearDto = new SchoolYearDTO();
        yearDto.setId(5L);
        var inDto = new SchoolAchievementsDTO();
        inDto.setSchoolYear(yearDto);
        inDto.setTitle("T");
        inDto.setContent("C");

        // mapper -> entity
        var entityToSave = new SchoolAchievementsEntity();
        when(achievementsMapper.toEntity(inDto)).thenReturn(entityToSave);

        // school year ref
        var yearEntity = new SchoolYearEntity();
        when(schoolYearRepo.getReferenceById(5L)).thenReturn(yearEntity);

        // save (the service ignores returned value and maps the same instance)
        when(achievementsRepo.save(entityToSave)).thenReturn(entityToSave);

        // mapper back to DTO
        var outDto = new SchoolAchievementsDTO();
        when(achievementsMapper.toDTO(entityToSave)).thenReturn(outDto);

        var result = service.createAchievement(inDto);

        assertSame(outDto, result);
        // school year was attached
        assertSame(yearEntity, entityToSave.getSchoolYear());
        verify(achievementsRepo).save(entityToSave);
    }

    // ---------- updateAchievement ----------

    @Test
    void updateAchievement_throws_whenIdNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.updateAchievement(null, new SchoolAchievementsDTO()));
    }

    @Test
    void updateAchievement_updates_whenExists() {
        Long id = 10L;

        when(achievementsRepo.existsById(id)).thenReturn(true);

        // capture DTO passed to mapper to ensure id was set
        var dtoArg = ArgumentCaptor.forClass(SchoolAchievementsDTO.class);

        var mappedEntity = new SchoolAchievementsEntity();
        when(achievementsMapper.toEntity(dtoArg.capture())).thenReturn(mappedEntity);

        when(achievementsRepo.save(mappedEntity)).thenReturn(mappedEntity);

        var outDto = new SchoolAchievementsDTO();
        when(achievementsMapper.toDTO(mappedEntity)).thenReturn(outDto);

        var input = new SchoolAchievementsDTO(); // id will be set by service
        var result = service.updateAchievement(id, input);

        assertSame(outDto, result);
        assertEquals(id, dtoArg.getValue().getId(), "service should set DTO id before mapping");
        verify(achievementsRepo).save(mappedEntity);
    }

    @Test
    void updateAchievement_throws_whenNotFound() {
        when(achievementsRepo.existsById(99L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class,
                () -> service.updateAchievement(99L, new SchoolAchievementsDTO()));
    }

    // ---------- getAchievement ----------

    @Test
    void getAchievement_returnsMappedDto_whenFound() {
        var entity = new SchoolAchievementsEntity();
        when(achievementsRepo.findById(1L)).thenReturn(Optional.of(entity));

        var dto = new SchoolAchievementsDTO();
        when(achievementsMapper.toDTO(entity)).thenReturn(dto);

        assertSame(dto, service.getAchievement(1L));
    }

    @Test
    void getAchievement_throws_whenMissing() {
        when(achievementsRepo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getAchievement(2L));
    }

    // ---------- getAllAchievements ----------

    @Test
    void getAllAchievements_mapsAll() {
        var e1 = new SchoolAchievementsEntity();
        var e2 = new SchoolAchievementsEntity();
        when(achievementsRepo.findAll()).thenReturn(List.of(e1, e2));

        var d1 = new SchoolAchievementsDTO();
        var d2 = new SchoolAchievementsDTO();
        when(achievementsMapper.toDTO(e1)).thenReturn(d1);
        when(achievementsMapper.toDTO(e2)).thenReturn(d2);

        var result = service.getAllAchievements();
        assertEquals(List.of(d1, d2), result);
    }

    // ---------- deleteAchievement ----------

    @Test
    void deleteAchievement_deletes_andReturnsDto_whenExists() {
        Long id = 7L;
        when(achievementsRepo.existsById(id)).thenReturn(true);

        var entity = new SchoolAchievementsEntity();
        when(achievementsRepo.findById(id)).thenReturn(Optional.of(entity));

        var dto = new SchoolAchievementsDTO();
        when(achievementsMapper.toDTO(entity)).thenReturn(dto);

        var result = service.deleteAchievement(id);

        assertSame(dto, result);
        verify(achievementsRepo).delete(entity);
    }

    @Test
    void deleteAchievement_throws_whenNotFound() {
        when(achievementsRepo.existsById(777L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteAchievement(777L));
    }

    // ---------- getAchievementsByYear ----------

    @Test
    void getAchievementsByYear_mapsList() {
        Long yearId = 3L;
        var e1 = new SchoolAchievementsEntity();
        var e2 = new SchoolAchievementsEntity();
        when(achievementsRepo.getAllAchievementsByYear(yearId)).thenReturn(List.of(e1, e2));

        var d1 = new SchoolAchievementsDTO();
        var d2 = new SchoolAchievementsDTO();
        when(achievementsMapper.toDTO(e1)).thenReturn(d1);
        when(achievementsMapper.toDTO(e2)).thenReturn(d2);

        var result = service.getAchievementsByYear(yearId);
        assertEquals(List.of(d1, d2), result);
    }
}
