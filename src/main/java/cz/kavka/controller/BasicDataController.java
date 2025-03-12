package cz.kavka.controller;

import cz.kavka.dto.BasicDataDTO;
import cz.kavka.service.serviceInterface.BasicDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/basicData")
public class BasicDataController {

    private BasicDataService basicDataService;

    @Autowired
    public BasicDataController(BasicDataService basicDataService){
        this.basicDataService = basicDataService;
    }

    @PostMapping("/create")
    public BasicDataDTO creatNewData(BasicDataDTO basicDataDTO){
        return basicDataService.create(basicDataDTO);
    }
}
