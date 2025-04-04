package cz.kavka.controller;

import cz.kavka.dto.RequiredInformationDTO;
import cz.kavka.service.serviceinterface.RequiredInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/static/required-info")
public class RequiredInformationController {

    private final RequiredInformationService requiredInformationService;

    @Autowired
    public RequiredInformationController(RequiredInformationService requiredInformationService) {
        this.requiredInformationService = requiredInformationService;
    }

    @PostMapping("/create-or-edit")
    public RequiredInformationDTO createInfo(@RequestBody RequiredInformationDTO requiredInformationDTO) throws IOException {
        return requiredInformationService.createOrEdit(requiredInformationDTO);
    }

    @GetMapping
    public RequiredInformationDTO showRequiredInfo() throws IOException{
        return requiredInformationService.getInfo();
    }
}
