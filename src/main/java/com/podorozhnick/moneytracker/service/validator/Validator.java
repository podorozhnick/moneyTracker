package com.podorozhnick.moneytracker.service.validator;

import com.podorozhnick.moneytracker.service.exception.ServiceValidationException;
import com.podorozhnick.moneytracker.util.ThrowableConsumer;

interface Validator<T> {

    default void validate(ThrowableConsumer<T, ServiceValidationException> validator, T object) throws ServiceValidationException {
        validator.accept(object);
    }

}
