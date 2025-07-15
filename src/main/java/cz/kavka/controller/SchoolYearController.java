package cz.kavka.controller;

import cz.kavka.dto.SchoolYearDTO;
import cz.kavka.service.serviceinterface.SchoolYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/school-year")
public class SchoolYearController {

    private final SchoolYearService schoolYearService;

    @Autowired
    SchoolYearController(SchoolYearService schoolYearService){
        this.schoolYearService = schoolYearService;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/create")
    public SchoolYearDTO create(@RequestBody SchoolYearDTO schoolYearDTO){
        return schoolYearService.create(schoolYearDTO);
    }

    @GetMapping("/{id}")
    public SchoolYearDTO showSchoolYear(@PathVariable Long id){
        return schoolYearService.get(id);
    }

    @GetMapping
    public List<SchoolYearDTO> getAllYears(){
        return schoolYearService.getAll();
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public SchoolYearDTO deleteYear(@PathVariable Long id){
        return schoolYearService.delete(id);
    }
}
