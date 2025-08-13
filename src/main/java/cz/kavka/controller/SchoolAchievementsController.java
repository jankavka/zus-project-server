package cz.kavka.controller;

import cz.kavka.dto.SchoolAchievementsDTO;
import cz.kavka.dto.SchoolYearDTO;
import cz.kavka.service.SchoolAchievementsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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

    @Secured("ROLE_ADMIN")
    @PostMapping("/create")
    public SchoolAchievementsDTO createAchievement (@RequestBody SchoolAchievementsDTO schoolAchievementsDTO){
        return schoolAchievementsService.createAchievement(schoolAchievementsDTO);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/edit/{id}")
    public SchoolAchievementsDTO editAchievement (@PathVariable Long id, @RequestBody SchoolAchievementsDTO schoolAchievementsDTO){
        return schoolAchievementsService.updateAchievement(id, schoolAchievementsDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public SchoolAchievementsDTO deleteAchievement (@PathVariable Long id){
        return schoolAchievementsService.deleteAchievement(id);
    }

    @GetMapping("/{id}")
    public SchoolAchievementsDTO showAchievement(@PathVariable Long id){
        return schoolAchievementsService.getAchievement(id);
    }

    @GetMapping("/year/{yearId}")
    public List<SchoolAchievementsDTO> getAchievementsByYear(@PathVariable Long yearId){
        return schoolAchievementsService.getAchievementsByYear(yearId);
    }
}
