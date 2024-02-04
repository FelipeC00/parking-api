package com.example.demoparkapi.resources.exceptions;

import com.example.demoparkapi.services.exceptions.DataBaseException;
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
        HttpStatus status= HttpStatus.BAD_REQUEST;
        ErrorMessage err = new ErrorMessage(request, status, e.getMessage());

        log.error("Api error: ", e);
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(err);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException e, HttpServletRequest request){
        HttpStatus status= HttpStatus.NOT_FOUND;
        ErrorMessage err = new ErrorMessage(request, status, e.getMessage());

        log.error("Api error: ", e);
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(err);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<ErrorMessage> dataBaseException(DataBaseException e, HttpServletRequest request){
        HttpStatus status= HttpStatus.CONFLICT;
        ErrorMessage err = new ErrorMessage(request, status, e.getMessage());

        log.error("Api error: ", e);
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                        HttpServletRequest request,
                                                                        BindingResult result){
        HttpStatus status= HttpStatus.UNPROCESSABLE_ENTITY;
        ErrorMessage err = new ErrorMessage(request, status, e.getMessage(), result);

        log.error("Api error: ", e);
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(err);
    }



}
