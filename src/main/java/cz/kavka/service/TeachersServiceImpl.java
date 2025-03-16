package cz.kavka.service;

import cz.kavka.dto.TeachersDTO;
import cz.kavka.dto.mapper.TeachersMapper;
import cz.kavka.entity.TeachersEntity;
import cz.kavka.entity.repository.TeachersRepository;
import cz.kavka.service.serviceInterface.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public TeachersDTO create(TeachersDTO teachersDTO) {
        TeachersEntity savedEntity = teachersRepository.save(teachersMapper.toEntity(teachersDTO));
        return teachersMapper.toDTO(savedEntity);
    }

    @Override
    public TeachersDTO edit(TeachersDTO teachersDTO, Long id) {
        teachersDTO.setId(id);
        TeachersEntity editedEntity = teachersRepository.save(teachersMapper.toEntity(teachersDTO));
        return teachersMapper.toDTO(editedEntity);
    }
}
