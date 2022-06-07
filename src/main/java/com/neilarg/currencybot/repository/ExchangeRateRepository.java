package com.neilarg.currencybot.repository;

import com.neilarg.currencybot.model.Bank;
import com.neilarg.currencybot.model.Currency;
import com.neilarg.currencybot.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    ExchangeRate findByBankAndCurrency(Bank bank, Currency currency);

    List<ExchangeRate> findByCurrency(Currency currency);


}
