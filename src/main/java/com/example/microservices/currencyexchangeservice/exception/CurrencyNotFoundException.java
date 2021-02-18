package com.example.microservices.currencyexchangeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Currency not found exception.
 *
 * @author Ihar Pashkevich
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Currency not found")
public class CurrencyNotFoundException extends RuntimeException {
    /**
     * Instantiates a new Currency not found exception.
     */
    public CurrencyNotFoundException() {
        super();
    }
}
