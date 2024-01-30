package com.web.ecomm.app.config;

import com.web.ecomm.app.exceptions.AuthenticationException;
import com.web.ecomm.app.exceptions.BusinessException;
import com.web.ecomm.app.exceptions.ResourceNotFoundException;
import com.web.ecomm.app.exceptions.SystemException;
import com.web.ecomm.app.exceptions.UnexpectedErrorException;
import com.web.ecomm.app.exceptions.ValidationException;
import com.web.ecomm.app.models.response.APIResponseEntity;
import com.web.ecomm.app.utils.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            ResourceNotFoundException.class,
            com.web.ecomm.app.exceptions.AuthenticationException.class,
            org.springframework.security.core.AuthenticationException.class,
            io.jsonwebtoken.security.SignatureException.class,
            java.security.SignatureException.class,
            ExpiredJwtException.class,
            UnexpectedErrorException.class,
            IOException.class,
            ConstraintViolationException.class,
            InvalidDataAccessResourceUsageException.class,
            BusinessException.class,
            SystemException.class,
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
        } else if (e instanceof org.springframework.security.core.AuthenticationException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_AUTHENTICATION, getReqId(request)
                    ),
                    HttpStatus.OK
            );
        } else if (e instanceof AccessDeniedException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_AUTHORIZATION, getReqId(request)
                    ),
                    HttpStatus.OK
            );
        } else if (e instanceof io.jsonwebtoken.security.SignatureException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_AUTHORIZATION, getReqId(request)
                    ),
                    HttpStatus.OK
            );
        } else if (e instanceof ExpiredJwtException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_AUTHORIZATION, getReqId(request)
                    ),
                    HttpStatus.OK
            );
        } else if (e instanceof java.security.SignatureException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_AUTHORIZATION, getReqId(request)
                    ),
                    HttpStatus.OK
            );
        } else if (e instanceof com.web.ecomm.app.exceptions.AuthenticationException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), ((AuthenticationException) e).getCode(), getReqId(request)
                    ),
                    HttpStatus.OK
            );
        } else if (e instanceof UnexpectedErrorException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), ((UnexpectedErrorException) e).getCode(), getReqId(request)
                    ),
                    HttpStatus.OK);
        } else if (e instanceof IOException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_IO, getReqId(request)
                    ),
                    HttpStatus.OK
            );
        } else if (e instanceof ConstraintViolationException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_IO, getReqId(request)
                    ),
                    HttpStatus.OK
            );
        } else if (e instanceof InvalidDataAccessResourceUsageException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), Constants.ERR_INVALID_DATA, getReqId(request)
                    ),
                    HttpStatus.OK
            );
        } else if (e instanceof ValidationException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), ((ValidationException) e).getCode(), getReqId(request)
                    ),
                    HttpStatus.OK
            );
        } else if (e instanceof BusinessException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), ((BusinessException) e).getCode(), getReqId(request)
                    ),
                    HttpStatus.OK
            );
        } else if (e instanceof SystemException) {
            return new ResponseEntity<>(
                    new APIResponseEntity<>(
                            Constants.STATUS_ERROR, e.getMessage(), ((SystemException) e).getCode(), getReqId(request)
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException e,
                                                                  @NonNull HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        logError(e);

        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(
                new APIResponseEntity<>(
                        Constants.STATUS_ERROR, errorMap.toString(), status.toString(), getReqId(request)
                ),
                HttpStatus.OK
        );
    }

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
