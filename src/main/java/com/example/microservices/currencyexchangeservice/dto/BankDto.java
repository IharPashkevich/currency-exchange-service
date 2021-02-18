package com.example.microservices.currencyexchangeservice.dto;

import lombok.Data;

import java.util.Set;


/**
 * Bank dto.
 *
 * @author Ihar Pashkevich
 */
@Data
public class BankDto {

    private String name;
    private String updateRateDate;
    private Set<CurrencyDto> currencies;
}
