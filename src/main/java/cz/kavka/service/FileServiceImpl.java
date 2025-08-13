package cz.kavka.service;

import cz.kavka.service.serviceinterface.FileService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {

    private final String filePathString;

    public FileServiceImpl(@Value("${files.file-path:data/pdf_files}") String filePathString) {
        this.filePathString = filePathString;
    }


    @Override
    public ResponseEntity<byte[]> getPdf(String fileName) throws IOException {
        Path filePath = Path.of(filePathString).toAbsolutePath().normalize();
        Path target = Path.of(filePath + File.separator + fileName);

        if (!Files.isDirectory(filePath)) {
            throw new FileNotFoundException("Base PDF directory not found: " + filePath);

        }

        if (!Files.isRegularFile(target)) {
            throw new FileNotFoundException("File not found: " + filePath.getFileName());
        }

        byte[] pdf = Files.readAllBytes(target);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileName)
                //Also header
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @Override
    public void uploadPdf(String fileName, String filePathFromPc) throws IOException {
        File oldFile = new File(filePathString + fileName);
        File newFile = new File(filePathFromPc);
        FileUtils.copyFile(newFile, oldFile);
    }
}
