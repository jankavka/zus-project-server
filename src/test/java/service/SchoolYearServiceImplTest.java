package service;

import cz.kavka.dto.SchoolYearDTO;
import cz.kavka.dto.mapper.SchoolYearMapper;
import cz.kavka.entity.SchoolYearEntity;
import cz.kavka.entity.repository.SchoolYearRepository;
import cz.kavka.service.SchoolYearServiceImpl;
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
class SchoolYearServiceImplTest {

    @Mock SchoolYearMapper mapper;
    @Mock SchoolYearRepository repo;

    @InjectMocks
    SchoolYearServiceImpl service;

    // -------- create --------

    @Test
    void create_throws_whenDtoNull() {
        assertThrows(IllegalArgumentException.class, () -> service.create(null));
    }

    @Test
    void create_saves_andReturnsMappedDto() {
        var inDto = new SchoolYearDTO();
        var entity = new SchoolYearEntity();
        var saved = new SchoolYearEntity();
        var outDto = new SchoolYearDTO();

        when(mapper.toEntity(inDto)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(outDto);

        var result = service.create(inDto);

        assertSame(outDto, result);
        verify(repo).save(entity);
    }

    // -------- edit --------

    @Test
    void edit_throws_whenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> service.edit(new SchoolYearDTO(), null));
    }

    @Test
    void edit_throws_whenDtoNull() {
        assertThrows(IllegalArgumentException.class, () -> service.edit(null, 1L));
    }

    @Test
    void edit_updates_whenExists() {
        Long id = 10L;
        when(repo.existsById(id)).thenReturn(true);

        var dtoCaptor = ArgumentCaptor.forClass(SchoolYearDTO.class);
        var mapped = new SchoolYearEntity();
        var saved = new SchoolYearEntity();
        var outDto = new SchoolYearDTO();

        when(mapper.toEntity(dtoCaptor.capture())).thenReturn(mapped);
        when(repo.save(mapped)).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(outDto);

        var input = new SchoolYearDTO(); // service sets id before mapping
        var result = service.edit(input, id);

        assertSame(outDto, result);
        assertEquals(id, dtoCaptor.getValue().getId(), "service should set DTO id before mapping");
        verify(repo).save(mapped);
    }

    @Test
    void edit_throws_whenNotFound() {
        when(repo.existsById(99L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.edit(new SchoolYearDTO(), 99L));
    }

    // -------- get --------

    @Test
    void get_returnsMapped_whenFound() {
        var entity = new SchoolYearEntity();
        var dto = new SchoolYearDTO();
        when(repo.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        assertSame(dto, service.get(1L));
    }

    @Test
    void get_throws_whenMissing() {
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.get(2L));
    }

    // -------- getAll --------

    @Test
    void getAll_mapsAll() {
        var e1 = new SchoolYearEntity();
        var e2 = new SchoolYearEntity();
        when(repo.findAll()).thenReturn(List.of(e1, e2));

        var d1 = new SchoolYearDTO();
        var d2 = new SchoolYearDTO();
        when(mapper.toDTO(e1)).thenReturn(d1);
        when(mapper.toDTO(e2)).thenReturn(d2);

        assertEquals(List.of(d1, d2), service.getAll());
    }

    // -------- delete --------

    @Test
    void delete_throws_whenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> service.delete(null));
    }

    @Test
    void delete_deletes_andReturnsMapped_whenFound() {
        Long id = 7L;
        var entity = new SchoolYearEntity();
        var dto = new SchoolYearDTO();

        when(repo.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        var result = service.delete(id);

        assertSame(dto, result);
        verify(repo).delete(entity);
    }

    @Test
    void delete_throws_whenMissing() {
        when(repo.findById(777L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.delete(777L));
    }
}

