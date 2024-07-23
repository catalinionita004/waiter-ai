package com.horeca.WaiterAi.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * When an error is thrown and persisted until it reaches the controller,
 * this class will act as an interceptor to catch the errors and return
 * the appropriate status code and message
 * This class use ErrorDetails to custom the response format
 * Example:
 * {
 * "timestamp": "2022-08-26T07:57:15.942+00:00",
 * "errorCode": "resource-not-found-exception",
 * "message": "Incorrect username or password",
 * "status": "404"
 * }
 * Link doc : <a href="https://www.studytonight.com/spring-boot/spring-boot-global-exception-handling">doc</a>
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * for handling global exception
     *
     * @param exception Exception
     * @param request   request
     * @return a response which contains an object details and a status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(new Date(), "global-exception", exception.getMessage(), String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),false);

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConverterException.class)
    public ResponseEntity<?> handleGlobalException(ConverterException exception, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(new Date(), "converter-exception", exception.getMessage(), String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),false);

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    /**
     * for handling validation exception
     *
     * @param exception BindException
     * @param request   request
     * @return a response which contains an object details and a status
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindException(BindException exception, WebRequest request) {
        StringBuilder messages = new StringBuilder();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            messages.append(error.getDefaultMessage()).append(";");
        });
        ErrorDetails errorDetails =
                new ErrorDetails(new Date(), "bad-request", messages.toString(), String.valueOf(HttpStatus.BAD_REQUEST.value()),false);

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
