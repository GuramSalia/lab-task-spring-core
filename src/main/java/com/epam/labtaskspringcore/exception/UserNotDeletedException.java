package com.epam.labtaskspringcore.exception;

public class UserNotDeletedException extends RuntimeException{
    public UserNotDeletedException(String message) {
        super(message);
    }
}
