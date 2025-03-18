package cz.kavka.controller;

import cz.kavka.dto.SchoolYearDTO;
import cz.kavka.service.serviceinterface.SchoolYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/school-year")
public class SchoolYearController {

    private SchoolYearService schoolYearService;

    @Autowired
    SchoolYearController(SchoolYearService schoolYearService){
        this.schoolYearService = schoolYearService;
    }

    @PostMapping("/create")
    public SchoolYearDTO create(@RequestBody SchoolYearDTO schoolYearDTO){
        return schoolYearService.create(schoolYearDTO);
    }
}
