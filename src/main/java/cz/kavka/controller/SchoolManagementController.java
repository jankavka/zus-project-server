package cz.kavka.controller;

import cz.kavka.dto.SchoolManagementDTO;
import cz.kavka.service.serviceInterface.SchoolManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/school-management")
public class SchoolManagementController {

    private final SchoolManagementService schoolManagementService;

    @Autowired
    public SchoolManagementController(SchoolManagementService schoolManagementService) {
        this.schoolManagementService = schoolManagementService;
    }

    @PostMapping("/create")
    public SchoolManagementDTO create(@RequestBody SchoolManagementDTO schoolManagementDTO){
        return schoolManagementService.create(schoolManagementDTO);
    }
}
