package com.podorozhnick.moneytracker.controller.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class RestException extends Exception {

    @JsonProperty
    private ErrorMessage errorMessage;

}
