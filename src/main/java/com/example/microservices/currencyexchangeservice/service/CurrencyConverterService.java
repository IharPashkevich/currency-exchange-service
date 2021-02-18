package com.example.microservices.currencyexchangeservice.service;

import java.math.BigDecimal;

/**
 * The interface Currency converter service.
 *
 * @author Ihar Pashkevich
 */
public interface CurrencyConverterService {

    /**
     * Convert currencies.
     *
     * @param amount       the amount of money
     * @param currencyFrom the original currency
     * @param currencyTo   the required currency
     * @return amount of the required currency
     * @throws IllegalArgumentException the illegal argument exception
     */
    BigDecimal convert(BigDecimal amount, String currencyFrom, String currencyTo) throws IllegalArgumentException;
}
