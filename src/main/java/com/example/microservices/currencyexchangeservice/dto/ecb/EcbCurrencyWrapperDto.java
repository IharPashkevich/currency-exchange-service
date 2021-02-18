package com.example.microservices.currencyexchangeservice.dto.ecb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * The Ecb currency wrapper dto.
 */
@JacksonXmlRootElement(localName = "Cube")
@Data
public class EcbCurrencyWrapperDto {

    @JacksonXmlProperty(localName = "Cube")
    private EcbCurrencyDateDto currencyDate;
}
