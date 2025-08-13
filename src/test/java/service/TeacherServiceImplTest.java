package service;

import cz.kavka.dto.TeachersDTO;
import cz.kavka.dto.mapper.TeachersMapper;
import cz.kavka.entity.TeachersEntity;
import cz.kavka.entity.repository.TeachersRepository;
import cz.kavka.service.TeachersServiceImpl;
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
class TeachersServiceImplTest {

    @Mock TeachersRepository repo;
    @Mock TeachersMapper mapper;

    @InjectMocks
    TeachersServiceImpl service;

    // -------- createTeacher --------

    @Test
    void createTeacher_throws_whenDtoNull() {
        assertThrows(IllegalArgumentException.class, () -> service.createTeacher(null));
    }

    @Test
    void createTeacher_saves_andReturnsMappedDto() {
        TeachersDTO in = new TeachersDTO();
        TeachersEntity mapped = new TeachersEntity();
        TeachersEntity saved = new TeachersEntity();
        TeachersDTO out = new TeachersDTO();

        when(mapper.toEntity(in)).thenReturn(mapped);
        when(repo.save(mapped)).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(out);

        TeachersDTO result = service.createTeacher(in);

        assertSame(out, result);
        verify(repo).save(mapped);
    }

    // -------- editTeacher --------

    @Test
    void editTeacher_throws_whenDtoNull() {
        assertThrows(IllegalArgumentException.class, () -> service.editTeacher(null, 1L));
    }

    @Test
    void editTeacher_updates_whenExists() {
        Long id = 42L;
        when(repo.existsById(id)).thenReturn(true);

        ArgumentCaptor<TeachersDTO> dtoCaptor = ArgumentCaptor.forClass(TeachersDTO.class);
        TeachersEntity mapped = new TeachersEntity();
        TeachersEntity saved = new TeachersEntity();
        TeachersDTO out = new TeachersDTO();

        when(mapper.toEntity(dtoCaptor.capture())).thenReturn(mapped);
        when(repo.save(mapped)).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(out);

        TeachersDTO input = new TeachersDTO(); // service should set id on this before mapping
        TeachersDTO result = service.editTeacher(input, id);

        assertSame(out, result);
        assertEquals(id, dtoCaptor.getValue().getId(), "service should set DTO id before mapping");
        verify(repo).save(mapped);
    }

    @Test
    void editTeacher_throws_whenNotFound() {
        when(repo.existsById(99L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.editTeacher(new TeachersDTO(), 99L));
    }

    // -------- getTeacher --------

    @Test
    void getTeacher_returnsMapped_whenFound() {
        TeachersEntity entity = new TeachersEntity();
        TeachersDTO dto = new TeachersDTO();

        when(repo.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        assertSame(dto, service.getTeacher(1L));
    }

    @Test
    void getTeacher_throws_whenMissing() {
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getTeacher(2L));
    }

    // -------- getAll --------

    @Test
    void getAll_mapsAll() {
        TeachersEntity e1 = new TeachersEntity();
        TeachersEntity e2 = new TeachersEntity();
        when(repo.findAll()).thenReturn(List.of(e1, e2));

        TeachersDTO d1 = new TeachersDTO();
        TeachersDTO d2 = new TeachersDTO();
        when(mapper.toDTO(e1)).thenReturn(d1);
        when(mapper.toDTO(e2)).thenReturn(d2);

        assertEquals(List.of(d1, d2), service.getAll());
    }

    // -------- deleteTeacher --------

    @Test
    void deleteTeacher_deletes_andReturnsMapped_whenFound() {
        Long id = 7L;
        TeachersEntity entity = new TeachersEntity();
        TeachersDTO dto = new TeachersDTO();

        when(repo.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        TeachersDTO result = service.deleteTeacher(id);

        assertSame(dto, result);
        verify(repo).delete(entity);
    }

    @Test
    void deleteTeacher_throws_whenMissing() {
        when(repo.findById(777L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.deleteTeacher(777L));
    }
}
