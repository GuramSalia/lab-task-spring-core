package com.epam.labtaskspringcore.exception;

public class UserNotUpdatedException extends RuntimeException{
    public UserNotUpdatedException(String message) {
        super(message);
    }
}
