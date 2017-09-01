package com.podorozhnick.moneytracker.controller.exception;

public class BadCredentialsResponseException extends ResponseException {

    public BadCredentialsResponseException() {
        super(new ResponseMessage(401, "Bad username or password", "Bad Credentials"));
    }
}
