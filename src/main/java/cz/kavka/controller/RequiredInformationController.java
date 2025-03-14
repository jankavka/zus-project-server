package cz.kavka.controller;

import cz.kavka.dto.RequiredInformationDTO;
import cz.kavka.service.serviceInterface.RequiredInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/required-info")
public class RequiredInformationController {

    private final RequiredInformationService requiredInformationService;

    @Autowired
    public RequiredInformationController(RequiredInformationService requiredInformationService) {
        this.requiredInformationService = requiredInformationService;
    }

    @PostMapping("/create")
    public RequiredInformationDTO create(@RequestBody RequiredInformationDTO requiredInformationDTO) {
        return requiredInformationService.create(requiredInformationDTO);
    }
}
