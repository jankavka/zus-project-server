package cz.kavka.controller;

import cz.kavka.dto.PersonalDataProtectionDTO;
import cz.kavka.service.PersonalDataProtectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personal-data-protection")
public class PersonalDataProtectionController {

    private final PersonalDataProtectionService personalDataProtectionService;

    @Autowired
    public PersonalDataProtectionController(PersonalDataProtectionService personalDataProtectionService){
        this.personalDataProtectionService = personalDataProtectionService;
    }

    @PostMapping("/create")
    public PersonalDataProtectionDTO create (@RequestBody PersonalDataProtectionDTO personalDataProtectionDTO){
        return personalDataProtectionService.createOrEdit(personalDataProtectionDTO);
    }

    @PutMapping("/edit")
    public PersonalDataProtectionDTO edit (@RequestBody PersonalDataProtectionDTO personalDataProtectionDTO){
        return personalDataProtectionService.createOrEdit(personalDataProtectionDTO);
    }

    @GetMapping
    public PersonalDataProtectionDTO showPersonalDataProtection(){
        return personalDataProtectionService.get();
    }
}
