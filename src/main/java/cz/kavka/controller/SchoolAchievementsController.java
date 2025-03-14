package cz.kavka.controller;

import cz.kavka.dto.SchoolAchievementsDTO;
import cz.kavka.service.SchoolAchievementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shool-achievements")
public class SchoolAchievementsController {

    private final SchoolAchievementsService schoolAchievementsService;

    @Autowired
    public SchoolAchievementsController(SchoolAchievementsService schoolAchievementsService){
        this.schoolAchievementsService = schoolAchievementsService;
    }

    @PostMapping("/create")
    public SchoolAchievementsDTO create (@RequestBody SchoolAchievementsDTO schoolAchievementsDTO){
        return schoolAchievementsService.create(schoolAchievementsDTO);
    }
}
