package cz.kavka.controller;

import cz.kavka.dto.BasicDataDTO;
import cz.kavka.service.serviceinterface.BasicDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/basicData")
public class BasicDataController {

    private final BasicDataService basicDataService;

    @Autowired
    public BasicDataController(BasicDataService basicDataService){
        this.basicDataService = basicDataService;
    }

    @PostMapping("/create")
    public BasicDataDTO create(@RequestBody BasicDataDTO basicDataDTO){
        return basicDataService.createOrEdit(basicDataDTO);
    }

    @PutMapping("/edit")
    public BasicDataDTO edit(@RequestBody BasicDataDTO basicDataDTO){
        return basicDataService.createOrEdit(basicDataDTO);
    }
}
