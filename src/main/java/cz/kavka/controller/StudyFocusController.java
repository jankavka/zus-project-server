package cz.kavka.controller;

import cz.kavka.dto.StudyFocusDTO;
import cz.kavka.service.StudyFocusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/study-focus")
public class StudyFocusController {

    private final StudyFocusService studyFocusService;

    @Autowired
    public StudyFocusController(StudyFocusService studyFocusService) {
        this.studyFocusService = studyFocusService;
    }

    @PostMapping("/create")
    public StudyFocusDTO create(@RequestBody StudyFocusDTO studyFocusDTO) {
        return studyFocusService.createOrEdit(studyFocusDTO);
    }

    @PutMapping("/edit")
    public StudyFocusDTO edit(@RequestBody StudyFocusDTO studyFocusDTO) {
        return studyFocusService.createOrEdit(studyFocusDTO);
    }

    @GetMapping
    public StudyFocusDTO showStudyFocus(){
        return studyFocusService.get();
    }
}
