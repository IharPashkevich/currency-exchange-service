package com.example.microservices.currencyexchangeservice.repository;

import com.example.microservices.currencyexchangeservice.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Exchange rate repository.
 *
 * @author Ihar Pashkevich
 */
@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {

    /**
     * Find by currency name optional.
     *
     * @param currencyName the currency name
     * @return the optional
     */
    Optional<ExchangeRate> findByCurrency(String currencyName);
}
