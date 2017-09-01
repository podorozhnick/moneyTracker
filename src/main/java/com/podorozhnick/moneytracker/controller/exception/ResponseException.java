package com.podorozhnick.moneytracker.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseException extends Exception {

    private ResponseMessage responseMessage;

}
