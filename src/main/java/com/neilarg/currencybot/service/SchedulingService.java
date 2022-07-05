package com.neilarg.currencybot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SchedulingService {

    private final ParsingService parsingService;
    private final ExchangeRateService exchangeRateService;

    @Autowired
    public SchedulingService(ParsingService parsingService, ExchangeRateService exchangeRateService
    ) {
        this.parsingService = parsingService;
        this.exchangeRateService = exchangeRateService;
    }

    @Scheduled(fixedDelay = 10 * 60000)
    private void scheduledParsingExchangeRates() {
        try {
            log.info("[START_EXCHANGE_RATES_UPDATING]");
            parsingService.parseCurrencies().forEach(exchangeRateService::updateExchangeRate);
            log.info("[FINISH_EXCHANGE_RATES_UPDATING]");
        } catch (Exception e) {
            log.error("[EXCHANGE_RATES_UPDATING_ERROR]", e);
        }
    }

}
