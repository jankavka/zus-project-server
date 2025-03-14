package cz.kavka.controller;

import cz.kavka.dto.SchoolFeeDTO;
import cz.kavka.service.SchoolFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/school-fee")
public class SchoolFeeController {

    private SchoolFeeService schoolFeeService;

    @Autowired
    public SchoolFeeController(SchoolFeeService schoolFeeService) {
        this.schoolFeeService = schoolFeeService;
    }

    @PostMapping("/create")
    public SchoolFeeDTO create(@RequestBody SchoolFeeDTO schoolFeeDTO) {
        return schoolFeeService.create(schoolFeeDTO);
    }
}
