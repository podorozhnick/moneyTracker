package com.podorozhnick.moneytracker.service.exception;

import com.podorozhnick.moneytracker.controller.exception.ErrorMessage;

public class NoSuchParentCategoryException extends ServiceLayerException {

    public NoSuchParentCategoryException() {
        super(new ErrorMessage("No such parent category"));
    }
}
