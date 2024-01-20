package com.app.centralExceptionHandler;

import com.app.customExceptions.AuthenticationException;
import com.app.customExceptions.ResourceNotFoundException;
import com.app.customExceptions.UnexpectedErrorException;
import com.app.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<ResponseDTO>(new ResponseDTO(false,"Item Not Found", e.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseDTO> handleAuthenticationException(AuthenticationException e) {
        return new ResponseEntity<ResponseDTO>(new ResponseDTO(false,"Authentication Failed", e.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(UnexpectedErrorException.class)
    public ResponseEntity<ResponseDTO> handleUnexpectedErrorException(UnexpectedErrorException e) {
        return new ResponseEntity<ResponseDTO>(new ResponseDTO(false,"Unexpected Error Occurred", e.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseDTO> handleIOException(IOException e) {
        return new ResponseEntity<ResponseDTO>(new ResponseDTO(false,"IO Exception Occurred", e.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleException(IOException e) {
        return new ResponseEntity<ResponseDTO>(new ResponseDTO(false,"Exception Occurred", e.getMessage()), HttpStatus.OK);
    }
}
