package com.neilarg.currencybot.service;

import com.neilarg.currencybot.dto.ParsedCurrencyDto;
import com.neilarg.currencybot.model.Bank;
import com.neilarg.currencybot.model.Currency;
import com.neilarg.currencybot.model.ExchangeRate;
import com.neilarg.currencybot.repository.CurrencyRepository;
import com.neilarg.currencybot.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExchangeRateService {

    private final BankService bankService;

    private final CurrencyRepository currencyRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeRateService(BankService bankService,
                               CurrencyRepository currencyRepository,
                               ExchangeRateRepository exchangeRateRepository) {
        this.bankService = bankService;
        this.currencyRepository = currencyRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public void updateExchangeRate(ParsedCurrencyDto dto) {
        Bank bank = bankService.getBank(dto.getBankName());
        if (bank!= null) {
            Currency currency = currencyRepository.findByCode(dto.getCurrencyCode());
            ExchangeRate exchangeRate = getExchangeRate(bank, currency);
            exchangeRate.setUpdateDate(new Date());
            exchangeRate.setPurchase(dto.getPurchase());
            exchangeRate.setSale(dto.getSale());
            exchangeRateRepository.save(exchangeRate);
        }
    }

    public ExchangeRate getExchangeRate(Bank bank, Currency currency) {
        ExchangeRate exchangeRate = exchangeRateRepository.findByBankAndCurrency(bank, currency);
        if (exchangeRate == null) {
            exchangeRate = new ExchangeRate();
            exchangeRate.setCurrency(currency);
            exchangeRate.setBank(bank);
        }
        return exchangeRate;
    }

}
