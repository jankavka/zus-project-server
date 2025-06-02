package cz.kavka.service;

import cz.kavka.service.serviceinterface.FileService;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileServiceImpl implements FileService {


    @Override
    public ResponseEntity<byte[]> getPdf(String fileName) throws IOException {
        String filePath = "data" + File.separator + "data/pdf_files" + File.separator;
        File file = new File(filePath + fileName);
        byte[] pdf = FileUtils.readFileToByteArray(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .header(HttpHeaders.CONTENT_TYPE, "application/x-pdf")
                .body(pdf);
    }

    @Override
    public void uploadPdf(String fileName, String filePathFromPc) throws IOException {
        String filePath = "data" + File.separator + "data/pdf_files" + File.separator;
        File oldFile = new File(filePath + fileName);
        File newFile = new File(filePathFromPc);
        FileUtils.copyFile(newFile, oldFile);
    }
}
