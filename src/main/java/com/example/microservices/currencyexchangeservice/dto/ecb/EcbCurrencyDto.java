package com.example.microservices.currencyexchangeservice.dto.ecb;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.math.BigDecimal;

/**
 * The Ecb currency dto.
 *
 * @author Ihar Pashkevich
 */
@JacksonXmlRootElement(localName = "Cube")
@Data
public class EcbCurrencyDto {
    @JacksonXmlProperty(localName = "currency")
    private String currency;

    @JacksonXmlProperty(localName = "rate")
    private BigDecimal rate;
}
