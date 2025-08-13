package cz.kavka.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralSecurityExceptionAdvice {

    @ExceptionHandler(GeneralSecurityException.class)
    public ResponseEntity<Map<String, String>> exceptionHandler(GeneralSecurityException e) {
        Map<String, String> body = new HashMap<>();
        body.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(body);
    }
}
