package cz.kavka.controller;

import cz.kavka.dto.GroupTrainingScheduleDTO;
import cz.kavka.service.GroupTrainingScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/group-training-schedule")
public class GroupTrainingScheduleController {

    private final GroupTrainingScheduleService groupTrainingScheduleService;

    @Autowired
    public GroupTrainingScheduleController(GroupTrainingScheduleService groupTrainingScheduleService){
        this.groupTrainingScheduleService = groupTrainingScheduleService;
    }

    @PostMapping("/create")
    public GroupTrainingScheduleDTO create (@RequestBody GroupTrainingScheduleDTO groupTrainingScheduleDTO){
        return groupTrainingScheduleService.create(groupTrainingScheduleDTO);
    }

}
