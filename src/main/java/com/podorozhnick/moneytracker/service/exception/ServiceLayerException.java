package com.podorozhnick.moneytracker.service.exception;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.podorozhnick.moneytracker.controller.exception.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ServiceLayerException extends Exception {

    @JsonProperty
    private ErrorMessage errorMessage;

}
