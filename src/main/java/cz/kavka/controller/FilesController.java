package cz.kavka.controller;

import cz.kavka.service.serviceinterface.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<byte[]> getPdfFile(@PathVariable String fileName) throws IOException {
        return fileService.getPdf(fileName);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/{fileName}")
    public void editPdfFile (@PathVariable String fileName, String filePathFromPc) throws IOException{
        fileService.uploadPdf(fileName, filePathFromPc);
    }
}
