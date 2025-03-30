package cz.kavka.controller;

import cz.kavka.dto.SchoolAchievementsDTO;
import cz.kavka.service.SchoolAchievementsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/school-achievements")
public class SchoolAchievementsController {

    private final SchoolAchievementsServiceImpl schoolAchievementsService;

    @Autowired
    public SchoolAchievementsController(SchoolAchievementsServiceImpl schoolAchievementsService){
        this.schoolAchievementsService = schoolAchievementsService;
    }

    @PostMapping("/create")
    public SchoolAchievementsDTO createAchievement (@RequestBody SchoolAchievementsDTO schoolAchievementsDTO){
        return schoolAchievementsService.createAchievement(schoolAchievementsDTO);
    }

    @PutMapping("/edit/{id}")
    public SchoolAchievementsDTO editAchievement (@PathVariable Long id, @RequestBody SchoolAchievementsDTO schoolAchievementsDTO){
        return schoolAchievementsService.updateAchievement(id, schoolAchievementsDTO);
    }

    @GetMapping
    public List<SchoolAchievementsDTO> showAllSchoolAchievements(){
        return schoolAchievementsService.getAllAchievements();
    }

    @DeleteMapping("/delete/{id}")
    public SchoolAchievementsDTO deleteAchievement (@PathVariable Long id){
        return schoolAchievementsService.deleteAchievement(id);
    }

    @GetMapping("/{id}")
    public SchoolAchievementsDTO showAchievement(@PathVariable Long id){
        return schoolAchievementsService.getAchievement(id);
    }
}
