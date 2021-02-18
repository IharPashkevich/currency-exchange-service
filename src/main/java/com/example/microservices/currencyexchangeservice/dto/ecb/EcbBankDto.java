package com.example.microservices.currencyexchangeservice.dto.ecb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * The Sender Ecb bank dto.
 *
 * @author Ihar Pashkevich
 */
@JacksonXmlRootElement(namespace = "gesmes", localName = "Sender")
@Data
public class EcbBankDto {

    @JacksonXmlProperty(namespace = "gesmes", localName = "name")
    private String name;
}
