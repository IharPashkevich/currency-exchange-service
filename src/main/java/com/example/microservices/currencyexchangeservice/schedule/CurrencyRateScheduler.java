package com.example.microservices.currencyexchangeservice.schedule;

import com.example.microservices.currencyexchangeservice.dto.BankDto;
import com.example.microservices.currencyexchangeservice.dto.CurrencyDto;
import com.example.microservices.currencyexchangeservice.entity.Bank;
import com.example.microservices.currencyexchangeservice.entity.ExchangeRate;
import com.example.microservices.currencyexchangeservice.repository.BankRepository;
import com.example.microservices.currencyexchangeservice.repository.ExchangeRateRepository;
import com.example.microservices.currencyexchangeservice.service.ExchangeRateMapper;
import com.example.microservices.currencyexchangeservice.service.ExchangeRateParser;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The Currency rate scheduler.
 * Update Currency rates
 *
 * @author Ihar Pashkevich
 */
@Component
@AllArgsConstructor
@Log4j2
public class CurrencyRateScheduler {

    private final ExchangeRateParser parser;

    private final BankRepository bankRepository;

    private final ExchangeRateRepository exchangeRateRepository;

    private final ExchangeRateMapper exchangeRateMapper;

    /**
     * Load current currency rates by schedule.
     */
    @Scheduled(cron = "${ecb.scheduled.cron}", zone = "${ecb.scheduled.timezone}")
    @Transactional
    public void loadCurrencyRates() {
        try {
            BankDto bankDto = parser.getActualCurrencyRates();

            log.debug("Actual currency {}", bankDto);

            Optional<Bank> bankOptional = bankRepository.findByName(bankDto.getName());

            if (bankOptional.isPresent()) {
                updateBankData(bankOptional.get(), bankDto.getUpdateRateDate(), bankDto.getCurrencies());
            } else {
                createBankData(bankDto);
            }
        } catch (IOException e) {
            log.warn("Parse exception {}", e);
        }
    }

    /**
     * Update current bank currencies data.
     */
    private void updateBankData(Bank bank, String updateDate, Set<CurrencyDto> currencyDtos) {
        if (!updateDate.equals(bank.getUpdateRateDate())) {

            Set<ExchangeRate> oldRates = bank.getExchangeRates();

            deleteOutdatedCurrencies(oldRates, currencyDtos);
            createNewCurrencies(oldRates, currencyDtos, bank);
            updateCurrencies(oldRates, currencyDtos, bank);

            bank.setUpdateRateDate(updateDate);
            bankRepository.save(bank);
        }
    }

    /**
     * Update currencies data.
     */
    private void updateCurrencies(Set<ExchangeRate> oldRates, Set<CurrencyDto> currentCurrencyDtos, Bank bank) {

        Map<String, ExchangeRate> oldRatesMap = oldRates.stream()
                .collect(Collectors.toMap(ExchangeRate::getCurrency, Function.identity()));

        for (CurrencyDto currentCurrencyDto : currentCurrencyDtos) {
            ExchangeRate exchangeRate = oldRatesMap.get(currentCurrencyDto.getCurrency());

            if (exchangeRate != null) {
                if (!exchangeRate.getRate().equals(currentCurrencyDto.getRate())) {

                    exchangeRate.setRate(currentCurrencyDto.getRate());
                    exchangeRateRepository.save(exchangeRate);
                }
            }
        }
    }

    /**
     * Create new currencies.
     */
    private void createNewCurrencies(Set<ExchangeRate> oldRates, Set<CurrencyDto> currentCurrencyDtos, Bank bank) {
        Set<String> oldCurrencies = oldRates.stream().map(ExchangeRate::getCurrency).collect(Collectors.toSet());

        Set<CurrencyDto> newCurrencies = currentCurrencyDtos.stream()
                .filter(dto -> !oldCurrencies.contains(dto.getCurrency())).collect(Collectors.toSet());

        log.debug("New currencies {}", newCurrencies);

        if (!newCurrencies.isEmpty()) {
            Set<ExchangeRate> newExchangeRates = newCurrencies.stream()
                    .map(dto -> exchangeRateMapper.currencyDtoToExchangeRate(dto, bank)).collect(Collectors.toSet());

            exchangeRateRepository.saveAll(newExchangeRates);
        }
    }

    /**
     * Delete outdated currencies.
     */
    private void deleteOutdatedCurrencies(Set<ExchangeRate> oldRates, Set<CurrencyDto> currentRates) {

        Set<String> newRates = currentRates
                .stream().map(CurrencyDto::getCurrency).collect(Collectors.toSet());

        Set<ExchangeRate> outdatedCurrencies = oldRates.stream()
                .filter(exchangeRate -> !newRates.contains(exchangeRate.getCurrency()))
                .collect(Collectors.toSet());

        log.debug("Outdated currencies {}", outdatedCurrencies);

        if (!outdatedCurrencies.isEmpty()) {
            exchangeRateRepository.deleteAll(outdatedCurrencies);
        }
    }

    /**
     * Create new bank with currencies.
     */
    private void createBankData(BankDto bankDto) {
        log.debug("Create new bank %s", bankDto.getName());

        Bank bank = new Bank();
        bank.setName(bankDto.getName());
        bank.setUpdateRateDate(bankDto.getUpdateRateDate());

        bankRepository.save(bank);

        Set<CurrencyDto> rates = bankDto.getCurrencies();

        Set<ExchangeRate> rateSet = rates.stream()
                .map(dto -> exchangeRateMapper.currencyDtoToExchangeRate(dto, bank)).collect(Collectors.toSet());

        log.debug("Rates in new bank {}", rateSet);
        exchangeRateRepository.saveAll(rateSet);
    }
}
