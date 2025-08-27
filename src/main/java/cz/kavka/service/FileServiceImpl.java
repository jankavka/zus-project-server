package cz.kavka.service;

import cz.kavka.dto.FileDTO;
import cz.kavka.dto.mapper.FileMapper;
import cz.kavka.entity.FileEntity;
import cz.kavka.entity.repository.FileRepository;
import cz.kavka.service.serviceinterface.FileService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final String filePathString;

    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    private final Object writeLock = new Object();

    @Autowired
    public FileServiceImpl(@Value("${files.file-path:data/pdf_files}") String filePathString, FileRepository fileRepository, FileMapper fileMapper) {
        this.filePathString = filePathString;
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
    }


    @Transactional(readOnly = true)
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

    @Transactional
    @Override
    public void uploadPdf(MultipartFile pdf, String fileName) throws IOException {

        if (!"application/pdf".equalsIgnoreCase(pdf.getContentType())) {
            throw new IllegalArgumentException("Error: Only pdf files accepted");
        }

        FileEntity entityToSave = new FileEntity();

        String normalizedFileName = normalizeFileName(fileName);

        System.out.println(normalizedFileName);

        synchronized (writeLock) {
            File file = new File(filePathString, normalizedFileName);
            FileUtils.copyInputStreamToFile(pdf.getInputStream(), file);
        }

        entityToSave.setNormalizedFileName(normalizedFileName);
        entityToSave.setFileName(fileName);
        entityToSave.setUrl("/api/files/" + normalizedFileName);
        fileRepository.save(entityToSave);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FileDTO> getAllFiles() {
        return fileRepository.findAll().stream().map(fileMapper::toDTO).toList();
    }

    @Transactional
    @Override
    public ResponseEntity<HttpStatus> deleteFile(Long id) throws IOException {
        FileEntity entityToDelete = fileRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        System.out.println("Path to delete: " + filePathString + File.separator + entityToDelete.getNormalizedFileName());

        synchronized (writeLock) {
            File fileToDelete = new File(filePathString, entityToDelete.getNormalizedFileName());
            FileUtils.delete(fileToDelete);
        }

        fileRepository.delete(entityToDelete);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private static String normalizeFileName(String fileName) {
        return Normalizer.normalize(fileName, Normalizer.Form.NFD)
                .replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}+", "")
                .replaceAll("\\[^a-zA-Z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase()
                .trim();
    }


}
