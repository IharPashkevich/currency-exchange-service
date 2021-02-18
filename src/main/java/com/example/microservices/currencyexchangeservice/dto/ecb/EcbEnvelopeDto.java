package com.example.microservices.currencyexchangeservice.dto.ecb;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * The main wrapper Ecb bank dto.
 *
 * @author Ihar Pashkevich
 */
@JacksonXmlRootElement(namespace = "gesmes", localName = "Envelope")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class EcbEnvelopeDto {

    @JacksonXmlProperty(localName = "Cube")
    private EcbCurrencyWrapperDto currencyWrapper;

    @JacksonXmlProperty(namespace = "gesmes", localName = "Sender")
    private EcbBankDto sender;

}
