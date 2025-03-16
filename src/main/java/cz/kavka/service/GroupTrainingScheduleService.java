package cz.kavka.service;

import cz.kavka.dto.GroupTrainingScheduleDTO;
import cz.kavka.dto.mapper.GroupTrainingScheduleMapper;
import cz.kavka.entity.GroupTrainingScheduleEntity;
import cz.kavka.entity.repository.GroupTrainingScheduleRepository;
import cz.kavka.service.serviceInterface.NameAndContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupTrainingScheduleService implements NameAndContentService<GroupTrainingScheduleDTO> {

    private final GroupTrainingScheduleMapper groupTrainingScheduleMapper;

    private final GroupTrainingScheduleRepository groupTrainingScheduleRepository;

    @Autowired
    public GroupTrainingScheduleService(GroupTrainingScheduleMapper groupTrainingScheduleMapper, GroupTrainingScheduleRepository groupTrainingScheduleRepository) {
        this.groupTrainingScheduleMapper = groupTrainingScheduleMapper;
        this.groupTrainingScheduleRepository = groupTrainingScheduleRepository;
    }

    @Override
    public GroupTrainingScheduleDTO createOrEdit(GroupTrainingScheduleDTO groupTrainingScheduleDTO) {
        GroupTrainingScheduleEntity savedEntity = groupTrainingScheduleRepository.save(groupTrainingScheduleMapper.toEntity(groupTrainingScheduleDTO));
        return groupTrainingScheduleMapper.toDTO(savedEntity);
    }

}
