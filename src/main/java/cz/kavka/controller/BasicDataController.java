package cz.kavka.controller;

import cz.kavka.dto.BasicDataDTO;
import cz.kavka.service.serviceinterface.BasicDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/static/basic-data")
public class BasicDataController {

    private final BasicDataService basicDataService;

    @Autowired
    public BasicDataController(BasicDataService basicDataService){
        this.basicDataService = basicDataService;
    }

    @PostMapping("/create-or-edit")
    public BasicDataDTO createData(@RequestBody BasicDataDTO basicDataDTO) throws IOException, URISyntaxException {
        return basicDataService.createOrEditBasicData(basicDataDTO);
    }

    @GetMapping
    public BasicDataDTO showData() throws IOException, URISyntaxException {
        return basicDataService.getBasicData();
    }

}
