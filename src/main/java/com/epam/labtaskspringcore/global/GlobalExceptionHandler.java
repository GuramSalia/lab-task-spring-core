package com.epam.labtaskspringcore.global;

import com.epam.labtaskspringcore.exception.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final CorrelationIDHandler correlationIDHandler;
    private final ObjectMapper objectMapper;

    @Autowired
    public GlobalExceptionHandler(CorrelationIDHandler correlationIDHandler, ObjectMapper objectMapper) {
        this.correlationIDHandler = correlationIDHandler;
        this.objectMapper = objectMapper;
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


        log.info("\n\n>> from error handler" + request.getParameterMap() + "\n");
        logRestDetails(ResponseEntity.badRequest().body(errorDetails));
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<ErrorDetails> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                correlationIDHandler.getCorrelationId(),
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                request.getDescription(false)
        );

        log.info("\n\n>> from error handler" + request.getDescription(false)+ "\n");
        ResponseEntity<ErrorDetails> body = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        logRestDetails(body);
        return body;
    }

    @ExceptionHandler(UserNotCreatedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<ErrorDetails> handleUserNotCreatedException(UserNotCreatedException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                correlationIDHandler.getCorrelationId(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getDescription(false)
        );

        ResponseEntity<ErrorDetails> body = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        logRestDetails(body);
        return body;
    }

    @ExceptionHandler(TrainingNotCreatedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<ErrorDetails> handleTrainingNotCreatedException(TrainingNotCreatedException ex,
                                                                          WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                correlationIDHandler.getCorrelationId(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getDescription(false)
        );

        ResponseEntity<ErrorDetails> body = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        logRestDetails(body);
        return body;
    }

    @ExceptionHandler(UserNotUpdatedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<ErrorDetails> handleUserNotUpdatedException(UserNotUpdatedException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                correlationIDHandler.getCorrelationId(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getDescription(false)
        );

        ResponseEntity<ErrorDetails> body = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        logRestDetails(body);
        return body;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                correlationIDHandler.getCorrelationId(),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );

        ResponseEntity<ErrorDetails> body = ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        logRestDetails(body);
        return body;
    }

    @ExceptionHandler(TrainingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<ErrorDetails> handleTrainingNotFoundException(TrainingNotFoundException ex,
                                                                        WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                correlationIDHandler.getCorrelationId(),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );

        ResponseEntity<ErrorDetails> body = ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        logRestDetails(body);
        return body;
    }

    @ExceptionHandler(UserNotDeletedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<ErrorDetails> handleUserNotDeletedException(UserNotDeletedException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                correlationIDHandler.getCorrelationId(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getDescription(false)
        );

        ResponseEntity<ErrorDetails> body = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        logRestDetails(body);
        return body;
    }

    @ExceptionHandler(Exception.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                correlationIDHandler.getCorrelationId(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getDescription(false)
        );
        ResponseEntity<ErrorDetails> body = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        logRestDetails(body);
        return body;
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

    //logging for task 17.2 when there are errors
    private void logRestDetails(ResponseEntity<ErrorDetails> responseEntity) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            // Logging request details
            //            log.info("\n\n-\tREST endpoint: {}\n", request.getReq());
            log.info("\n\n-\tREST URI: {}\n", request.getRequestURI());
            log.info("\n\n-\tRequest method: {}\n", request.getMethod());

            //Logging request body: it can be read once and when it comes to handler it can no longer be read.

            // Logging request parameters
            log.info("\n\n-\tRequest parameters:");
            request.getParameterMap().forEach((name, values) -> {
                log.info("\n\n-\t\t{}: {}", name, values);
            });

            // Logging response details
            if (responseEntity != null) {

                log.info("\n\n-\tResponse Code: {}\n", responseEntity.getStatusCode().value());
                log.info("\n\n-\tResponse Body: {}\n", responseEntity.getBody());

                if (responseEntity.getBody() != null) {
                    log.info("\n\n-\tMessage: {}\n", responseEntity.getBody().getMessage());
                }
            }
        }
    }
}
