package com.epam.labtaskspringcore.global;

import com.epam.labtaskspringcore.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final CorrelationIDHandler correlationIDHandler;

    @Autowired
    public GlobalExceptionHandler(CorrelationIDHandler correlationIDHandler) {
        this.correlationIDHandler = correlationIDHandler;
    }


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                correlationIDHandler.getCorrelationId(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String finalErrorMessage = getErrorMessagesFromFieldErrors(fieldErrors);

        ErrorDetails errorDetails = new ErrorDetails(
                correlationIDHandler.getCorrelationId(),
                HttpStatus.BAD_REQUEST,
                finalErrorMessage,
                request.getDescription(false)
        );

        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorDetails> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                correlationIDHandler.getCorrelationId(),
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
    }

    private String getErrorMessagesFromFieldErrors(List<FieldError> fieldErrors) {
        StringBuilder errorMessage = new StringBuilder();
        for (int i = 0; i < fieldErrors.size(); i++) {
            FieldError error = fieldErrors.get(i);
            errorMessage.append((i + 1)).append(".").append(error.getDefaultMessage());

            if (i < fieldErrors.size() - 1) {
                errorMessage.append(", ");
            }
        }
        return errorMessage.toString();
    }

    private void logRestDetails() {
        // REST endpoint called:
        // Request parameters for endpoint
        // Response Code:
        // error Message:
        // Response Body:

        // call logRestDetails from all methods in GlobalExceptionHandler

    }


}
