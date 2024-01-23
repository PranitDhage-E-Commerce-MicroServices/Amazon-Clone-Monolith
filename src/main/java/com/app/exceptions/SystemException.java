package com.app.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 *
 * System Exception Structure
 *
 */

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unexpected Error")
public class SystemException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String code = null;

    @Setter
    private String message = "";

    public SystemException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
