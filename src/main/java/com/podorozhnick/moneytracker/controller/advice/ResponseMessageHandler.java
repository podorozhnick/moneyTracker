package com.podorozhnick.moneytracker.controller.advice;

import com.podorozhnick.moneytracker.controller.exception.ResponseException;
import com.podorozhnick.moneytracker.controller.exception.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResponseMessageHandler {

    @ExceptionHandler(ResponseException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage handleResponseException(ResponseException exception) {
        return exception.getResponseMessage();
    }

}
