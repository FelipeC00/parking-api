package com.example.demoparkapi.services.exceptions;

public class UserNameUniqueViolationException extends RuntimeException{
    public UserNameUniqueViolationException(String message) {
        super(message);
    }
}
