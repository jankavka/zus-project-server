package cz.kavka.service.serviceinterface;

import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {

    ResponseEntity<byte[]> getPdf(String fileName) throws IOException;

    void uploadPdf(String fileName, String filePathFromPc) throws IOException;
}
