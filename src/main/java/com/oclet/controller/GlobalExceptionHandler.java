package com.oclet.controller;

import com.oclet.controller.dto.ApiError;
import com.oclet.exception.CustomerNotFoundException;
import com.oclet.exception.UnsupportedVersionException;
import com.oclet.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Global Exception Handler, handles all exceptions that make it through the @RestController.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer does not exist";
    private static final String PATH_NOT_FOUND_MESSAGE = "API path / method combination not found";
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";
    private static final String UNSUPPORTED_VERSION_ERROR_MESSAGE = "API version unsupported";

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleValidationException(ValidationException exp) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exp.getMessage())
                .build();
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({CustomerNotFoundException.class})
    public ResponseEntity<Object> handleItemNotFoundException() {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(CUSTOMER_NOT_FOUND_MESSAGE)
                .build();
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({MethodNotAllowedException.class, NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Object> handleUnknownApiExceptions() {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(PATH_NOT_FOUND_MESSAGE)
                .build();
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({UnsupportedVersionException.class})
    public ResponseEntity<Object> handleUnsupportedApiExceptions() {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(UNSUPPORTED_VERSION_ERROR_MESSAGE)
                .build();
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException() {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(INTERNAL_SERVER_ERROR_MESSAGE)
                .build();
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

}
