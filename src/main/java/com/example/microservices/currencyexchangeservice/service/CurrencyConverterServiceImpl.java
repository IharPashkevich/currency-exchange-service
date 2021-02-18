package com.example.microservices.currencyexchangeservice.service;

import com.example.microservices.currencyexchangeservice.entity.ExchangeRate;
import com.example.microservices.currencyexchangeservice.repository.ExchangeRateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * The implementation of  Currency converter service.
 *
 * @author Ihar Pashkevich
 */
@Service
@AllArgsConstructor
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    private final ExchangeRateRepository rateRepository;

    private static final String EUR = "eur";

    private static final ExchangeRate EUR_RATE = new ExchangeRate(BigDecimal.ONE);

    /**
     * Convert currencies.
     *
     * @param amount       the amount of money
     * @param currencyFrom the original currency
     * @param currencyTo   the required currency
     * @return amount of the required currency
     * @throws IllegalArgumentException the illegal argument exception
     */
    @Override
    public BigDecimal convert(BigDecimal amount, String currencyFrom, String currencyTo) throws IllegalArgumentException {
        ExchangeRate exchangeRateTo = findByCurrency(currencyTo);
        ExchangeRate exchangeRateFrom = findByCurrency(currencyFrom);

        return amount.divide(exchangeRateFrom.getRate(), 4, RoundingMode.CEILING)
                .multiply(exchangeRateTo.getRate()).setScale(2, RoundingMode.CEILING);

    }

    /**
     * Find exchange rate by name.
     *
     * @param currencyName the currency name

     * @throws IllegalArgumentException the illegal argument exception when unknown currency
     */
    private ExchangeRate findByCurrency(String currencyName) throws IllegalArgumentException {
        Optional<ExchangeRate> currencyOptional = rateRepository.findByCurrency(currencyName);

        if (!currencyOptional.isPresent() && !currencyName.equals(EUR)) {
            throw new IllegalArgumentException("Unknown currency exception");
        }

        return currencyOptional.orElse(EUR_RATE);
    }
}
