package com.podorozhnick.moneytracker.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class ValidationErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<String>> handleValidationError(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        Map<String, List<String>> validationErrors = processFieldErrors(result.getFieldErrors());
        return validationErrors;
    }

    private Map<String, List<String>> processFieldErrors(List<FieldError> fieldErrors) {
        Map<String, List<String>> errorMap = new HashMap<>();
        for (FieldError fieldError: fieldErrors) {
            errorMap.putIfAbsent(fieldError.getField(), new ArrayList<>());
            errorMap.get(fieldError.getField()).add(fieldError.getDefaultMessage());
        }
        return errorMap;
    }

}
