package cz.kavka.controller;

import cz.kavka.dto.FileDTO;
import cz.kavka.service.serviceinterface.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FilesController {

    private final FileService fileService;

    @Autowired
    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getPdfFile(@PathVariable String fileName) throws IOException {
        return fileService.getPdf(fileName);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public void editPdfFile(
            @RequestParam("fileName") String fileName, @RequestParam("file") MultipartFile pdf) throws IOException {
        fileService.uploadPdf(pdf, fileName);
    }

    @GetMapping
    public List<FileDTO> showAllFiles() {
        return fileService.getAllFiles();
    }

    @GetMapping("/section/{section}")
    public List<FileDTO> showAllFilesBySection(@PathVariable String section){
        return null;
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeFile(@PathVariable Long id) throws IOException{
        return fileService.deleteFile(id);
    }
}
