package com.example.microservices.currencyexchangeservice.repository;

import com.example.microservices.currencyexchangeservice.entity.Bank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Bank repository.
 *
 * @author Ihar Pashkevich
 */
@Repository
public interface BankRepository extends CrudRepository<Bank, Integer> {

    /**
     * Find Bank by name optional.
     *
     * @param name the Bank name
     * @return the optional
     */
    @Query("SELECT b FROM Bank b left join fetch b.exchangeRates WHERE b.name = ?1")
    Optional<Bank> findByName(String name);
}
