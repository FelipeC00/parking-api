package com.example.demoparkapi.services.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(Object id) {
        super("Entity not found. Id "+id);
    }
}
