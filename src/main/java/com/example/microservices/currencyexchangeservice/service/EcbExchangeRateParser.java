package com.example.microservices.currencyexchangeservice.service;

import com.example.microservices.currencyexchangeservice.dto.BankDto;
import com.example.microservices.currencyexchangeservice.dto.CurrencyDto;
import com.example.microservices.currencyexchangeservice.dto.ecb.EcbCurrencyDto;
import com.example.microservices.currencyexchangeservice.dto.ecb.EcbEnvelopeDto;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The European Central Bank exchange rate parser.
 *
 * @author Ihar Pashkevich
 */
@Component
public class EcbExchangeRateParser implements ExchangeRateParser {

    @Value("${ecb.url.rates}")
    private String urlRates;

    /**
     * Get actual currency rate parser.
     *

     * @throws IOException the connect, read or parse exception
     */
    public BankDto getActualCurrencyRates() throws IOException {
        URL url = new URL(urlRates);
        XmlMapper mapper = new XmlMapper();

        EcbEnvelopeDto ecbDto = mapper.readValue(url, EcbEnvelopeDto.class);

        return ecbDtoToBankDto(ecbDto);
    }

    /**
     * Convert ecb currencies dto to the bank dto.
     *
     * @param ecbDto       ecb currencies dto
     * @return bankDto     bank currencies dto
     */
    private BankDto ecbDtoToBankDto(EcbEnvelopeDto ecbDto) {
        String bankName = ecbDto.getSender().getName();
        String updateDate = ecbDto.getCurrencyWrapper().getCurrencyDate().getDate();

        Set<EcbCurrencyDto> ecbRates = ecbDto.getCurrencyWrapper().getCurrencyDate().getRates();

        Set<CurrencyDto> currencyDtos = ecbRates.stream()
                .map(dto -> new CurrencyDto(dto.getCurrency(), dto.getRate())).collect(Collectors.toSet());

        BankDto bankDto = new BankDto();
        bankDto.setName(bankName);
        bankDto.setUpdateRateDate(updateDate);
        bankDto.setCurrencies(currencyDtos);

        return bankDto;
    }
}

