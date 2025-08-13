package cz.kavka.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MethodArgumentNotValidExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleArgumentNotValidException(){
        Map<String, String> body = new HashMap<>();
        body.put("message", "Internal Server Error: Not valid fields in the form");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(body);
    }
}
