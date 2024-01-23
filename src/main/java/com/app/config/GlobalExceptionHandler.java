package com.app.config;

import com.app.dto.ResponseDTO;
import com.app.exceptions.AuthenticationException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.exceptions.UnexpectedErrorException;
import com.app.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        return new ResponseEntity<>(
                new ResponseDTO<>(
                        false, "Item Not Found", e.getMessage(), getReqId(request)
                ),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e, WebRequest request) {
        return new ResponseEntity<>(
                new ResponseDTO<>(
                false, "Authentication Failed", e.getMessage(), getReqId(request)
                ),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(UnexpectedErrorException.class)
    public ResponseEntity<?> handleUnexpectedErrorException(UnexpectedErrorException e, WebRequest request) {
        return new ResponseEntity<>(
                new ResponseDTO<>(
                        false, "Unexpected Error Occurred", e.getMessage(), getReqId(request)
                ),
                HttpStatus.OK);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleUnexpectedErrorException(NoResourceFoundException e, WebRequest request) {
        return new ResponseEntity<>(
                new ResponseDTO<>(
                        false, "Resource No Found", e.getMessage(), getReqId(request)
                ),
                HttpStatus.OK);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException e, WebRequest request) {
        return new ResponseEntity<>(
                new ResponseDTO<>(
                        false, "IO Exception Occurred", e.getMessage(), getReqId(request)
                ),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(IOException e, WebRequest request) {
        return new ResponseEntity<>(
                new ResponseDTO<>(
                        false, "Exception Occurred", e.getMessage(), getReqId(request)
                ),
                HttpStatus.OK);
    }

    private static String getReqId(WebRequest request) {
        String reqId = request.getHeader(Constants.REQ_ID_KEY);
        if (StringUtils.isEmpty(reqId)) {
            reqId = MDC.get(Constants.REQ_ID_KEY);
        }

        MDC.clear();
        return reqId;
    }
}
