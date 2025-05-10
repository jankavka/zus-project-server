package cz.kavka.controller;

import cz.kavka.dto.SchoolManagementDTO;
import cz.kavka.service.serviceinterface.SchoolManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/school-management")
public class SchoolManagementController {

    private final SchoolManagementService schoolManagementService;

    @Autowired
    public SchoolManagementController(SchoolManagementService schoolManagementService) {
        this.schoolManagementService = schoolManagementService;
    }

    @PostMapping("/create")
    public SchoolManagementDTO createMember(@RequestBody @Valid SchoolManagementDTO schoolManagementDTO){
        return schoolManagementService.createMember(schoolManagementDTO);
    }

    @PutMapping("/{id}/edit")
    public SchoolManagementDTO edit(@RequestBody @Valid SchoolManagementDTO schoolManagementDTO, @PathVariable Long id){
        return schoolManagementService.editMember(schoolManagementDTO,id);
    }

    @GetMapping("/{id}")
    public SchoolManagementDTO showManagementMember(@PathVariable Long id){
        return schoolManagementService.getMember(id);
    }

    @GetMapping
    public List<SchoolManagementDTO> showAll(){
        return schoolManagementService.getAll();
    }

    @DeleteMapping("/{id}/delete")
    public SchoolManagementDTO deleteMember(@PathVariable Long id){
        return schoolManagementService.deleteMember(id);
    }

}
