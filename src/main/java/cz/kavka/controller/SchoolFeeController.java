package cz.kavka.controller;

import cz.kavka.dto.SchoolFeeDTO;
import cz.kavka.service.SchoolFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/school-fee")
public class SchoolFeeController {

    private final SchoolFeeService schoolFeeService;

    @Autowired
    public SchoolFeeController(SchoolFeeService schoolFeeService) {
        this.schoolFeeService = schoolFeeService;
    }

    @PostMapping("/create")
    public SchoolFeeDTO create(@RequestBody SchoolFeeDTO schoolFeeDTO) {
        return schoolFeeService.createOrEdit(schoolFeeDTO);
    }

    @PutMapping("/edit")
    public SchoolFeeDTO edit(@RequestBody SchoolFeeDTO schoolFeeDTO) {
        return schoolFeeService.createOrEdit(schoolFeeDTO);
    }
}
