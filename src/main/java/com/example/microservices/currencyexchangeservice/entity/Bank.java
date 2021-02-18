package com.example.microservices.currencyexchangeservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * The Bank entity.
 *
 * @author Ihar Pashkevich
 */
@Entity
@Table(name = "banks")
@Getter
@Setter
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "update_rate_date", nullable = false)
    private String updateRateDate;

    @OneToMany(mappedBy = "bank")
    private Set<ExchangeRate> exchangeRates;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bank)) return false;

        Bank bank = (Bank) o;

        if (!id.equals(bank.id)) return false;
        return name.equals(bank.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
