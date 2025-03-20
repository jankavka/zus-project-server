package cz.kavka.controller;

import cz.kavka.dto.RequiredInformationDTO;
import cz.kavka.service.serviceinterface.RequiredInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return requiredInformationService.createOrEdit(requiredInformationDTO);
    }

    @PutMapping("/edit")
    public RequiredInformationDTO edit(@RequestBody RequiredInformationDTO requiredInformationDTO) {
        return requiredInformationService.createOrEdit(requiredInformationDTO);
    }

    @GetMapping
    public RequiredInformationDTO showRequiredInfo(){
        return requiredInformationService.get();
    }
}
