package com.web.ecomm.app.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

public class APIUtil {

    public static void validateRequestBody(BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String message = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
            throw new ValidationException(message, Constants.ERR_INVALID_DATA);
        }
    }
}
