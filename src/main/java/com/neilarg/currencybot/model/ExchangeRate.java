package com.neilarg.currencybot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "exchange_rates", indexes = @Index(name = "exchange_rate_idx", columnList = "id, bank_id, currency_id, updateDate"))
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false, insertable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(name = "purchase", nullable = false)
    private Double purchase;

    @Column(name = "sale", nullable = false)
    private Double sale;

    @Column(name = "updateDate", nullable = false)
    private Date updateDate;

}
