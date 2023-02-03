package com.neilarg.currencybot.service;

import com.neilarg.currencybot.dto.ParsedCurrencyDto;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParsingService {

        @Value("${xpath.currency_row}")
        private String currencyRowXpath;

        private final CrawlerService crawlerService;

        @Autowired
        public ParsingService(CrawlerService crawlerService) {
                this.crawlerService = crawlerService;
        }

        public List<ParsedCurrencyDto> parseCurrencies() throws Exception {
                List<ParsedCurrencyDto> parsedCurrencies = new ArrayList<>();
                crawlerService.loadCurrenciesPage("minsk").selectXpath(currencyRowXpath)
                        .forEach(row -> parsedCurrencies.addAll(parseCurrencyRow(row)));
                return parsedCurrencies;
        }

        private List<ParsedCurrencyDto> parseCurrencyRow(Element row) {
                Elements columns = row.selectXpath(".//td");
                ParsedCurrencyDto usd = new ParsedCurrencyDto(columns.get(0).text(),
                                "USD",
                                Double.parseDouble(columns.get(1).text().equals("-") ? "0" : columns.get(1).text()),
                                Double.parseDouble(columns.get(2).text().equals("-") ? "0" : columns.get(1).text()));
                ParsedCurrencyDto eur = new ParsedCurrencyDto(columns.get(0).text(),
                                "EUR",
                                Double.parseDouble(columns.get(3).text().equals("-") ? "0" : columns.get(1).text()),
                                Double.parseDouble(columns.get(4).text().equals("-") ? "0" : columns.get(1).text()));
                ParsedCurrencyDto rub = new ParsedCurrencyDto(columns.get(0).text(),
                                "RUB",
                                Double.parseDouble(columns.get(5).text().equals("-") ? "0" : columns.get(1).text()),
                                 Double.parseDouble(columns.get(6).text().equals("-") ? "0" : columns.get(1).text()));
                return List.of(usd, eur, rub);
        }

}
