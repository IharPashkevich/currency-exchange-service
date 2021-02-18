package com.example.microservices.currencyexchangeservice.controller;

import com.example.microservices.currencyexchangeservice.dto.ConvertDataDto;
import com.example.microservices.currencyexchangeservice.exception.CurrencyNotFoundException;
import com.example.microservices.currencyexchangeservice.service.CurrencyConverterService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * The REST controller for convert currencies.
 * @author Ihar Pashkevich
 */
@RestController
@Log4j2
@AllArgsConstructor
public class ConverterController {

    private final CurrencyConverterService currencyConverterService;

    /**
     * Convert currencies.
     *
     * @param convertDataDto the convert dto
     * @return the big decimal amount of new currency
     */
    @PostMapping("/convert")
    public BigDecimal convert(@Valid @RequestBody ConvertDataDto convertDataDto) {

        if (convertDataDto.getCurrencyFrom().equals(convertDataDto.getCurrencyTo())) {
            return convertDataDto.getAmount();
        }

        if (convertDataDto.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            log.debug("Return zero");
            return BigDecimal.ZERO;
        }

        try {
            return currencyConverterService
                    .convert(convertDataDto.getAmount(), convertDataDto.getCurrencyFrom(), convertDataDto.getCurrencyTo());
        } catch (IllegalArgumentException e) {
            throw new CurrencyNotFoundException();
        }
    }
}
