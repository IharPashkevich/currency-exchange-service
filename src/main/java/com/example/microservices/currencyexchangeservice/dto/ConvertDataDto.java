package com.example.microservices.currencyexchangeservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * The Convert data dto.
 *
 * @author Ihar Pashkevich
 */
@Data
public class ConvertDataDto {

    @NotEmpty(message = "Please provide a currency from")
    private String currencyFrom;

    @NotEmpty(message = "Please provide a currency to")
    private String currencyTo;

    @NotNull(message = "Please provide amount")
    @DecimalMin(value = "0.0", message = "Please provide amount")
    private BigDecimal amount;

}
