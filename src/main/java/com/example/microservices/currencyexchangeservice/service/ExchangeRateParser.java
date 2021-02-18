package com.example.microservices.currencyexchangeservice.service;

import com.example.microservices.currencyexchangeservice.dto.BankDto;
import com.example.microservices.currencyexchangeservice.dto.ecb.EcbEnvelopeDto;

import java.io.IOException;

/**
 * The interface Exchange rate parser.
 */
public interface ExchangeRateParser {
    /**
     * Gets actual currency rates.
     *
     * @return the actual currency
     * @throws IOException the io exception
     */
    BankDto getActualCurrencyRates() throws IOException;
}
