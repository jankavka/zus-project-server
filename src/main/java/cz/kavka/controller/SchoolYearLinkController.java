package cz.kavka.controller;

import cz.kavka.dto.SchoolYearLinkDTO;
import cz.kavka.service.serviceinterface.SchoolYearLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/school-year-link")
public class SchoolYearLinkController {

    private final SchoolYearLinkService schoolYearLinkService;

    @Autowired
    public SchoolYearLinkController(SchoolYearLinkService schoolYearLinkService) {
        this.schoolYearLinkService = schoolYearLinkService;
    }

    @GetMapping
    public SchoolYearLinkDTO getSchoolYearLink() throws IOException {
        return schoolYearLinkService.get();
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    public SchoolYearLinkDTO updateSchoolYearLink(@RequestBody SchoolYearLinkDTO schoolYearLinkDTO) throws IOException {
        return schoolYearLinkService.update(schoolYearLinkDTO);
    }
}
