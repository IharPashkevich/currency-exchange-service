package com.example.microservices.currencyexchangeservice.controller;

import com.example.microservices.currencyexchangeservice.dto.ConvertDataDto;
import com.example.microservices.currencyexchangeservice.entity.ExchangeRate;
import com.example.microservices.currencyexchangeservice.repository.ExchangeRateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * The type Converter controller integration test.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class ConverterControllerIntegrationTest {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private MockMvc mockMvc;

    /**
     * When post request to converter and in valid amount then correct response.
     *
     * @throws Exception the exception
     */
    @Test
    public void whenPostRequestToConverterAndInValidAmount_thenCorrectResponse() throws Exception {

        ConvertDataDto convertDataDto = new ConvertDataDto();
        convertDataDto.setCurrencyFrom("eur");
        convertDataDto.setCurrencyTo("usd");
        convertDataDto.setAmount(new BigDecimal(-100));

        ObjectMapper objectMapper = new ObjectMapper();

        String data = objectMapper.writeValueAsString(convertDataDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/convert")
                .content(data)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * When post request to converter and in valid currency from then correct response.
     *
     * @throws Exception the exception
     */
    @Test
    public void whenPostRequestToConverterAndInValidCurrencyFrom_thenCorrectResponse() throws Exception {

        ConvertDataDto convertDataDto = new ConvertDataDto();
        convertDataDto.setCurrencyFrom("");
        convertDataDto.setCurrencyTo("usd");
        convertDataDto.setAmount(new BigDecimal(100));

        ObjectMapper objectMapper = new ObjectMapper();

        String data = objectMapper.writeValueAsString(convertDataDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/convert")
                .content(data)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * When post request to converter and in valid currency to then correct response.
     *
     * @throws Exception the exception
     */
    @Test
    public void whenPostRequestToConverterAndInValidCurrencyTo_thenCorrectResponse() throws Exception {

        ConvertDataDto convertDataDto = new ConvertDataDto();
        convertDataDto.setCurrencyFrom("eur");
        convertDataDto.setCurrencyTo("");
        convertDataDto.setAmount(new BigDecimal(100));

        ObjectMapper objectMapper = new ObjectMapper();

        String data = objectMapper.writeValueAsString(convertDataDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/convert")
                .content(data)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * When post request to converter and in valid currency to data then correct response.
     *
     * @throws Exception the exception
     */
    @Test
    public void whenPostRequestToConverterAndInValidCurrencyToData_thenCorrectResponse() throws Exception {

        ConvertDataDto convertDataDto = new ConvertDataDto();
        convertDataDto.setCurrencyFrom("eur");
        convertDataDto.setCurrencyTo("qqq");
        convertDataDto.setAmount(new BigDecimal(100));

        ObjectMapper objectMapper = new ObjectMapper();

        String data = objectMapper.writeValueAsString(convertDataDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/convert")
                .content(data)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * When post request to converter and valid data then correct response.
     *
     * @throws Exception the exception
     */
    @Test
    public void whenPostRequestToConverterAndValidData_thenCorrectResponse() throws Exception {

        String currencyFromName = "usd";
        String currencyToName = "jpy";
        BigDecimal amount = new BigDecimal(100);

        log.debug("exchangeRateRepository {}", exchangeRateRepository);

        Optional<ExchangeRate> currencyFromOp = exchangeRateRepository.findByCurrency(currencyFromName);
        Optional<ExchangeRate> currencyToOp = exchangeRateRepository.findByCurrency(currencyToName);

        log.debug("Currency from optional {}", currencyFromOp);
        log.debug("Currency to optional {}", currencyToOp);

        assertTrue(currencyFromOp.isPresent());
        assertTrue(currencyToOp.isPresent());

        BigDecimal result = amount.divide(currencyFromOp.get().getRate(), 4, RoundingMode.CEILING)
                .multiply(currencyToOp.get().getRate()).setScale(2, RoundingMode.CEILING);

        ConvertDataDto convertDataDto = new ConvertDataDto();
        convertDataDto.setCurrencyFrom(currencyFromName);
        convertDataDto.setCurrencyTo(currencyToName);
        convertDataDto.setAmount(amount);

        ObjectMapper objectMapper = new ObjectMapper();

        String data = objectMapper.writeValueAsString(convertDataDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/convert")
                .content(data)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertEquals(mvcResult.getResponse().getContentAsString(), result.toString());
    }
}