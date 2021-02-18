package com.example.microservices.currencyexchangeservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The Exchange rate entity.
 *
 * @author Ihar Pashkevich
 */
@Entity
@Table(name = "exchange_rates")
@Getter
@Setter
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "rate", nullable = false)
    private BigDecimal rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    /**
     * Instantiates a new Exchange rate.
     */
    public ExchangeRate() {
    }

    /**
     * Instantiates a new Exchange rate.
     *
     * @param rate the rate
     */
    public ExchangeRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangeRate)) return false;

        ExchangeRate that = (ExchangeRate) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (!currency.equals(that.currency)) return false;
        return rate.equals(that.rate);
    }

    @Override
    public int hashCode() {
        int result = currency.hashCode();
        result = 31 * result + rate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "id=" + id +
                ", currency='" + currency + '\'' +
                ", rate=" + rate +
                '}';
    }
}
