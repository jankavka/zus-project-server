package cz.kavka.service.serviceinterface;

import cz.kavka.dto.FileDTO;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileService {

    ResponseEntity<byte[]> getPdf(String fileName) throws IOException;

    void uploadPdf(MultipartFile pdf, String fileName) throws IOException;

    List<FileDTO> getAllFiles();

    ResponseEntity<HttpStatus> deleteFile(Long id) throws IOException;


}
