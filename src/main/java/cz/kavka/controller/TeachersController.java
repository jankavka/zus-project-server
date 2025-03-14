package cz.kavka.controller;

import cz.kavka.dto.TeachersDTO;
import cz.kavka.service.serviceInterface.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/teachers")
public class TeachersController {

    private final TeachersService teachersService;

    @Autowired
    public TeachersController(TeachersService teachersService) {
        this.teachersService = teachersService;
    }

    public TeachersDTO create (@RequestBody TeachersDTO teachersDTO){
        return teachersService.create(teachersDTO);
    }
}
