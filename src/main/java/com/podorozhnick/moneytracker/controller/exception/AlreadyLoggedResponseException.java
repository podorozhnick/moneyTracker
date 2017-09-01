package com.podorozhnick.moneytracker.controller.exception;

public class AlreadyLoggedResponseException extends ResponseException {

    public AlreadyLoggedResponseException() {
        super(new ResponseMessage(304, "User already logged in", "Logged in"));
    }
}
