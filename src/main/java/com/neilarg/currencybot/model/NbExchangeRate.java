package com.neilarg.currencybot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "nb_exchange_rates", indexes = @Index(name = "nb_exchange_rate_idx", columnList = "id, currency_id, update_date"))
public class NbExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false, insertable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(name = "current_rate", nullable = false)
    private Double currentRate;

    @Column(name = "tomorrow_rate", nullable = false)
    private Double tomorrowRate;

    @Column(name = "update_date", nullable = false)
    private Date updateDate;

}
