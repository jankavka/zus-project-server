package cz.kavka.controller;

import cz.kavka.dto.StudyFocusDTO;
import cz.kavka.service.StudyFocusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/study-focus")
public class StudyFocusController {

    private StudyFocusService studyFocusService;

    @Autowired
    public StudyFocusController(StudyFocusService studyFocusService){
        this.studyFocusService = studyFocusService;
    }

    @PostMapping("/create")
    public StudyFocusDTO create (@RequestBody StudyFocusDTO studyFocusDTO){
        return studyFocusService.create(studyFocusDTO);
    }
}
