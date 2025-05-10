package cz.kavka.controller;

import cz.kavka.dto.TeachersDTO;
import cz.kavka.service.serviceinterface.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeachersController {

    private final TeachersService teachersService;

    @Autowired
    public TeachersController(TeachersService teachersService) {
        this.teachersService = teachersService;
    }

    @PostMapping("/create")
    public TeachersDTO create (@RequestBody TeachersDTO teachersDTO){
        return teachersService.createTeacher(teachersDTO);
    }

    @PutMapping("/{id}/edit")
    public TeachersDTO edit(@RequestBody TeachersDTO teachersDTO, @PathVariable Long id){
        return teachersService.editTeacher(teachersDTO,id);
    }

    @GetMapping("/{id}")
    public TeachersDTO showTeacher(@PathVariable Long id){
        return teachersService.getTeacher(id);
    }

    @GetMapping
    public List<TeachersDTO> showAllTeachers(){
        return teachersService.getAll();
    }

    @DeleteMapping("/{id}/delete")
    public TeachersDTO removeTeacher(@PathVariable Long id){
        return teachersService.deleteTeacher(id);
    }
}
