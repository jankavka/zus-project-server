package cz.kavka.service;

import cz.kavka.dto.TeachersDTO;
import cz.kavka.dto.mapper.TeachersMapper;
import cz.kavka.entity.TeachersEntity;
import cz.kavka.entity.repository.TeachersRepository;
import cz.kavka.service.serviceinterface.TeachersService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeachersServiceImpl implements TeachersService {

    private final TeachersMapper teachersMapper;

    private final TeachersRepository teachersRepository;

    @Transactional
    @Override
    public TeachersDTO createTeacher(TeachersDTO teachersDTO) {
        if (teachersDTO == null) {
            throw new IllegalArgumentException("Error: Entity must not be null");
        }
        TeachersEntity savedEntity = teachersRepository.save(teachersMapper.toEntity(teachersDTO));
        return teachersMapper.toDTO(savedEntity);
    }

    @Transactional
    @Override
    public TeachersDTO editTeacher(TeachersDTO teachersDTO, Long id) {
        if (teachersDTO == null) {
            throw new IllegalArgumentException("Error: Entity must not be null");
        }
        if (teachersRepository.existsById(id)) {
            teachersDTO.setId(id);
            TeachersEntity editedEntity = teachersRepository.save(teachersMapper.toEntity(teachersDTO));
            return teachersMapper.toDTO(editedEntity);
        } else {
            throw new EntityNotFoundException("Error: Entity for update not found");
        }
    }

    @Override
    public TeachersDTO getTeacher(Long id) {
        return teachersMapper.toDTO(teachersRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<TeachersDTO> getAll() {
        return teachersRepository.findAll().stream().map(teachersMapper::toDTO).toList();
    }

    @Transactional
    @Override
    public TeachersDTO deleteTeacher(Long id) {
        TeachersEntity entityToDelete = teachersRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        teachersRepository.delete(entityToDelete);
        return teachersMapper.toDTO(entityToDelete);
    }


}
