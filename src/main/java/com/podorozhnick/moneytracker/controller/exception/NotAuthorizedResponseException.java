package com.podorozhnick.moneytracker.controller.exception;

public class NotAuthorizedResponseException extends ResponseException {

    public NotAuthorizedResponseException() {
        super(new ResponseMessage(401, "User Unauthorized", "Unauthorized"));
    }

}
