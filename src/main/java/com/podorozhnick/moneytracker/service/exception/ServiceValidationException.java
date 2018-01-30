package com.podorozhnick.moneytracker.service.exception;

import com.podorozhnick.moneytracker.controller.exception.ErrorMessage;

public class ServiceValidationException extends ServiceLayerException {
    public ServiceValidationException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
