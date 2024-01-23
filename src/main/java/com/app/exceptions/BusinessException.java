package com.app.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 *
 * Business Exception Structure
 *
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unexpected Input")
public class BusinessException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String code = null;

    @Setter
    private String message = "";

    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
