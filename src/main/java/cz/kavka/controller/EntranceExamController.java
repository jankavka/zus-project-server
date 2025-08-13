package cz.kavka.controller;

import com.google.api.services.calendar.Calendar;
import cz.kavka.dto.EntranceExamDTO;
import cz.kavka.service.serviceinterface.EntranceExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/entrance-exam")
public class EntranceExamController {

    private final EntranceExamService entranceExamService;

    @Autowired
    public EntranceExamController(EntranceExamService entranceExamService){
        this.entranceExamService = entranceExamService;
    }

    @GetMapping
    public EntranceExamDTO getEntranceExam() throws IOException {
        return entranceExamService.getEntranceExam();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public EntranceExamDTO updateEntranceExam(@RequestBody EntranceExamDTO entranceExamDTO) throws IOException{
        return entranceExamService.updateEntranceExam(entranceExamDTO);
    }

    @GetMapping("/is-hidden")
    public boolean isEntranceExamHidden() throws IOException{
        return entranceExamService.isHidden();
    }
}
