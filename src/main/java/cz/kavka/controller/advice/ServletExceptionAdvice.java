package cz.kavka.controller.advice;

import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServletExceptionAdvice {

    @ExceptionHandler(ServletException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleServletException(){

    }
}
