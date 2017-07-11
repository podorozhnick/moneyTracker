package com.podorozhnick.moneytracker.controller.exception;

import lombok.AllArgsConstructor;

public class NoContentException extends RestException {

    public NoContentException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public NoContentException() {
        setErrorMessage(new ErrorMessage("No Content"));
    }
}
