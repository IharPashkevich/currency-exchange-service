package com.example.microservices.currencyexchangeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * The Currency dto.
 *
 * @author Ihar Pashkevich
 */
@Data
@AllArgsConstructor
public class CurrencyDto {
    private String currency;
    private BigDecimal rate;
}
