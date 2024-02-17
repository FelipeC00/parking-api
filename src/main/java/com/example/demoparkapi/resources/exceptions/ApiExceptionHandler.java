package com.example.demoparkapi.resources.exceptions;

import com.example.demoparkapi.services.exceptions.UserNameUniqueViolationException;
import com.example.demoparkapi.services.exceptions.EntityNotFoundException;
import com.example.demoparkapi.services.exceptions.PasswordInvalidException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> passwordInvalidException(PasswordInvalidException e, HttpServletRequest request){

        log.error("Api error: ", e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException e, HttpServletRequest request){

        log.error("Api error: ", e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(UserNameUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> uniqueViolationException(UserNameUniqueViolationException e, HttpServletRequest request){

        log.error("Api error: ", e);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)//when the user provides info in an invalid format
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                        HttpServletRequest request, BindingResult result){
        log.error("Api error: ", e);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), result));
    }
}
