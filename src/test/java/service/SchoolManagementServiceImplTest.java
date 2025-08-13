package service;

import cz.kavka.dto.SchoolManagementDTO;
import cz.kavka.dto.mapper.SchoolManagementMapper;
import cz.kavka.entity.SchoolManagementEntity;
import cz.kavka.entity.repository.SchoolManagementRepository;
import cz.kavka.service.SchoolManagementServiceImpl;
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
class SchoolManagementServiceImplTest {

    @Mock SchoolManagementRepository repo;
    @Mock SchoolManagementMapper mapper;

    @InjectMocks
    SchoolManagementServiceImpl service;

    // ---------- createMember ----------

    @Test
    void createMember_throws_whenDtoNull() {
        assertThrows(IllegalArgumentException.class, () -> service.createMember(null));
    }

    @Test
    void createMember_saves_andReturnsMappedDto() {
        var inDto = new SchoolManagementDTO();
        var entity = new SchoolManagementEntity();
        var saved = new SchoolManagementEntity();
        var outDto = new SchoolManagementDTO();

        when(mapper.toEntity(inDto)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(outDto);

        var result = service.createMember(inDto);

        assertSame(outDto, result);
        verify(repo).save(entity);
    }

    // ---------- editMember ----------

    @Test
    void editMember_throws_whenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> service.editMember(new SchoolManagementDTO(), null));
    }

    @Test
    void editMember_updates_whenExists() {
        Long id = 42L;
        when(repo.existsById(id)).thenReturn(true);

        var dtoCaptor = ArgumentCaptor.forClass(SchoolManagementDTO.class);
        var mapped = new SchoolManagementEntity();
        var saved = new SchoolManagementEntity();
        var outDto = new SchoolManagementDTO();

        when(mapper.toEntity(dtoCaptor.capture())).thenReturn(mapped);
        when(repo.save(mapped)).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(outDto);

        var input = new SchoolManagementDTO(); // service will set id on this before mapping
        var result = service.editMember(input, id);

        assertSame(outDto, result);
        assertEquals(id, dtoCaptor.getValue().getId(), "service should set DTO id before mapping");
        verify(repo).save(mapped);
    }

    @Test
    void editMember_throws_whenNotFound() {
        when(repo.existsById(99L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class,
                () -> service.editMember(new SchoolManagementDTO(), 99L));
    }

    // ---------- getMember ----------

    @Test
    void getMember_returnsMapped_whenFound() {
        var entity = new SchoolManagementEntity();
        var dto = new SchoolManagementDTO();

        when(repo.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        assertSame(dto, service.getMember(1L));
    }

    @Test
    void getMember_throws_whenMissing() {
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getMember(2L));
    }

    // ---------- getAll ----------

    @Test
    void getAll_mapsAll() {
        var e1 = new SchoolManagementEntity();
        var e2 = new SchoolManagementEntity();
        when(repo.findAll()).thenReturn(List.of(e1, e2));

        var d1 = new SchoolManagementDTO();
        var d2 = new SchoolManagementDTO();
        when(mapper.toDTO(e1)).thenReturn(d1);
        when(mapper.toDTO(e2)).thenReturn(d2);

        var result = service.getAll();
        assertEquals(List.of(d1, d2), result);
    }

    // ---------- deleteMember ----------

    @Test
    void deleteMember_deletes_andReturnsDto_whenExists() {
        Long id = 7L;
        var entity = new SchoolManagementEntity();
        var dto = new SchoolManagementDTO();

        when(repo.existsById(id)).thenReturn(true);
        when(repo.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        var result = service.deleteMember(id);

        assertSame(dto, result);
        verify(repo).delete(entity);
    }

    @Test
    void deleteMember_throws_whenNotFound() {
        when(repo.existsById(777L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteMember(777L));
    }
}
