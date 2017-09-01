package com.podorozhnick.moneytracker.controller.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseMessage {

    @JsonProperty
    private Integer code;

    @JsonProperty
    private String message;

    @JsonProperty
    private String status;

}
