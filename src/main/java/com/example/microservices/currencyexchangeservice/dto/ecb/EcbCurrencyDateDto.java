package com.example.microservices.currencyexchangeservice.dto.ecb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.Set;

/**
 * The Ecb currency wrapper with dates and currencies date dto.
 *
 * @author Ihar Pashkevich
 */
@JacksonXmlRootElement(localName = "Cube")
@Data
public class EcbCurrencyDateDto {

    @JacksonXmlProperty(localName = "time")
    private String date;

    @JacksonXmlProperty(localName = "Cube")
    @JacksonXmlElementWrapper(useWrapping = false)
    private Set<EcbCurrencyDto> rates;
}
