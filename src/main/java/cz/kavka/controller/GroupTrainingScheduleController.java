package cz.kavka.controller;

import cz.kavka.dto.GroupTrainingScheduleDTO;
import cz.kavka.service.GroupTrainingScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return groupTrainingScheduleService.createOrEdit(groupTrainingScheduleDTO);
    }

    @PutMapping("/edit")
    public GroupTrainingScheduleDTO edit (@RequestBody GroupTrainingScheduleDTO groupTrainingScheduleDTO){
        return groupTrainingScheduleService.createOrEdit(groupTrainingScheduleDTO);
    }

}
