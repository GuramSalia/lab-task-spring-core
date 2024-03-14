package com.epam.labtaskspringcore.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorDetails  {
    private LocalDateTime localDateTime;
    private String message;
    private String details;

    public ErrorDetails(LocalDateTime localDateTime, String message, String details) {
        this.localDateTime = localDateTime;
        this.message = message;
        this.details = details;
    }
}
