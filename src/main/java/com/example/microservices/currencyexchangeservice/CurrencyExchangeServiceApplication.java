package com.example.microservices.currencyexchangeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The main class of Spring Boot application.
 */
@SpringBootApplication(scanBasePackages = {"com.example.microservices.currencyexchangeservice"})
@EnableScheduling
public class CurrencyExchangeServiceApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangeServiceApplication.class, args);
    }
}