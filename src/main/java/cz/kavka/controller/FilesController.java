package cz.kavka.controller;

import cz.kavka.service.serviceinterface.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FilesController {

    private final FileService fileService;

    @Autowired
    public FilesController(FileService fileService){
        this.fileService = fileService;
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getSchoolRules(@PathVariable String fileName) throws IOException {
        return fileService.getPdf(fileName);
    }

    @PostMapping("/{fileName}")
    public void editPdf (@PathVariable String fileName, String filePathFromPc) throws IOException{
        fileService.uploadPdf(fileName, filePathFromPc);
    }
}
