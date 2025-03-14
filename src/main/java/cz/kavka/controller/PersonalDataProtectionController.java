package cz.kavka.controller;

import cz.kavka.dto.PersonalDataProtectionDTO;
import cz.kavka.service.PersonalDataProtectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/personal-data-protection")
public class PersonalDataProtectionController {

    private PersonalDataProtectionService personalDataProtectionService;

    @Autowired
    public PersonalDataProtectionController(PersonalDataProtectionService personalDataProtectionService){
        this.personalDataProtectionService = personalDataProtectionService;
    }

    @PostMapping("/create")
    public PersonalDataProtectionDTO create (@RequestBody PersonalDataProtectionDTO personalDataProtectionDTO){
        return personalDataProtectionService.create(personalDataProtectionDTO);
    }
}
