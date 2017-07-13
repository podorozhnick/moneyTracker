package com.podorozhnick.moneytracker.controller.exception;

public class BadRequestException extends RestException {

    public BadRequestException() {
        super(new ErrorMessage("Bad Request"));
    }

    public BadRequestException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
