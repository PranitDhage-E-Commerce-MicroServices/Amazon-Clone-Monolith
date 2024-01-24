package com.web.ecomm.app.config;

import com.web.ecomm.app.dto.APIResponseEntity;
import com.web.ecomm.app.exceptions.AuthenticationException;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.exceptions.UnexpectedErrorException;
import com.web.ecomm.app.utils.Constants;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.xml.bind.ValidationException;
import java.io.IOException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            ResourceNotFoundException.class,
            AuthenticationException.class,
            UnexpectedErrorException.class,
            IOException.class,
            ConstraintViolationException.class,
            InvalidDataAccessResourceUsageException.class,
            Exception.class,
    })
    public ResponseEntity<?> handleAllException(Exception e, WebRequest request) {

        logError(e);

        if (e instanceof ResourceNotFoundException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), ((ResourceNotFoundException) e).getCode(), getReqId(request)
                    ),
                    HttpStatus.OK
            );
        } if (e instanceof AuthenticationException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), ((AuthenticationException) e).getCode(), getReqId(request)
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        } if (e instanceof UnexpectedErrorException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), ((UnexpectedErrorException) e).getCode(), getReqId(request)
                    ),
                    HttpStatus.OK);
        } if (e instanceof IOException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_IO, getReqId(request)
                    ),
                    HttpStatus.OK
            );
        } if (e instanceof ConstraintViolationException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_IO, getReqId(request)
                    ),
                    HttpStatus.OK
            );
        } if (e instanceof ValidationException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_INVALID_DATA, getReqId(request)
                    ),
                    HttpStatus.OK
            );
        } if (e instanceof InvalidDataAccessResourceUsageException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_INVALID_DATA, getReqId(request)
                    ),
                    HttpStatus.OK
            );
        } else  {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_EXCEPTION, getReqId(request)
                    ),
                    HttpStatus.OK);
        }
    }

/*
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        logError(e);
        return new ResponseEntity<>(
                new APIResponseEntity<>(
                        Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_RESOURCE_NOT_FOUND, getReqId(request)
                ),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e, WebRequest request) {
        logError(e);
        return new ResponseEntity<>(
                new APIResponseEntity<>(
                Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_AUTH, getReqId(request)
                ),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(UnexpectedErrorException.class)
    public ResponseEntity<?> handleUnexpectedErrorException(UnexpectedErrorException e, WebRequest request) {
        logError(e);
        return new ResponseEntity<>(
                new APIResponseEntity<>(
                        Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_EXCEPTION, getReqId(request)
                ),
                HttpStatus.OK);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e, WebRequest request) {
        logError(e);
        return new ResponseEntity<>(
                new APIResponseEntity<>(
                        Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_EXCEPTION, getReqId(request)
                ),
                HttpStatus.OK);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ValidationException e, WebRequest request) {
        logError(e);
        return new ResponseEntity<>(
                new APIResponseEntity<>(
                        Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_INVALID_DATA, getReqId(request)
                ),
                HttpStatus.OK);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException e, WebRequest request) {
        logError(e);
        return new ResponseEntity<>(
                new APIResponseEntity<>(
                        Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_INVALID_DATA, getReqId(request)
                ),
                HttpStatus.OK);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException e, WebRequest request) {
        logError(e);
        return new ResponseEntity<>(
                new APIResponseEntity<>(
                        Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_IO, getReqId(request)
                ),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(IOException e, WebRequest request) {
        logError(e);
        return new ResponseEntity<>(
                new APIResponseEntity<>(
                        Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_EXCEPTION, getReqId(request)
                ),
                HttpStatus.OK);
    }
*/

    private static String getReqId(WebRequest request) {
        String reqId = request.getHeader(Constants.REQ_ID_KEY);
        if (StringUtils.isEmpty(reqId)) {
            reqId = MDC.get(Constants.REQ_ID_KEY);
        }
        return reqId;
    }
    
    private static void logError(Exception e) {
        log.error("Exception Message: {}", e.getMessage());
        log.error("Exception Cause : {}", String.valueOf(e.getCause()));
        log.error("Exception Stacktrace : {}", (Object) e.getStackTrace());
//        e.printStackTrace();
    }

}
