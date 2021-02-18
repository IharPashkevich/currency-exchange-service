package com.example.microservices.currencyexchangeservice.service;

import com.example.microservices.currencyexchangeservice.dto.CurrencyDto;
import com.example.microservices.currencyexchangeservice.entity.Bank;
import com.example.microservices.currencyexchangeservice.entity.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
 * The interface Exchange rate mapper.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExchangeRateMapper {

    /**
     * Map from Currency dto to exchange rate entity.
     *
     * @param dto  the currency dto
     * @param bank the bank entity
     * @return the exchange rate entity
     */
    @Mappings({
            @Mapping(target="currency", source="dto.currency"),
            @Mapping(target="rate", source="dto.rate"),
            @Mapping(target="bank", source="bank")
    })
    ExchangeRate currencyDtoToExchangeRate(CurrencyDto dto, Bank bank);
}
