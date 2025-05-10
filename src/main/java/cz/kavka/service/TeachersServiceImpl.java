package cz.kavka.service;

import cz.kavka.dto.TeachersDTO;
import cz.kavka.dto.mapper.TeachersMapper;
import cz.kavka.entity.TeachersEntity;
import cz.kavka.entity.repository.TeachersRepository;
import cz.kavka.service.serviceinterface.TeachersService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeachersServiceImpl implements TeachersService {

    private final TeachersMapper teachersMapper;

    private final TeachersRepository teachersRepository;

    @Autowired
    public TeachersServiceImpl(TeachersRepository teachersRepository, TeachersMapper teachersMapper) {
        this.teachersRepository = teachersRepository;
        this.teachersMapper = teachersMapper;
    }

    @Override
    public TeachersDTO createTeacher(TeachersDTO teachersDTO) {
        TeachersEntity savedEntity = teachersRepository.save(teachersMapper.toEntity(teachersDTO));
        return teachersMapper.toDTO(savedEntity);
    }

    @Override
    public TeachersDTO editTeacher(TeachersDTO teachersDTO, Long id) {
        teachersDTO.setId(id);
        TeachersEntity editedEntity = teachersRepository.save(teachersMapper.toEntity(teachersDTO));
        return teachersMapper.toDTO(editedEntity);
    }

    @Override
    public TeachersDTO getTeacher(Long id) {
        return teachersMapper.toDTO(teachersRepository.getReferenceById(id));
    }

    @Override
    public List<TeachersDTO> getAll() {
        return teachersRepository.findAll().stream().map(teachersMapper::toDTO).toList();
    }

    @Override
    public TeachersDTO deleteTeacher(Long id) {
        TeachersEntity entityToDelete = teachersRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        teachersRepository.delete(entityToDelete);
        return teachersMapper.toDTO(entityToDelete);
    }


}
