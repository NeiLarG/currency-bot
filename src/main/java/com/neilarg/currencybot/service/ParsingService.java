package com.neilarg.currencybot.service;

import com.neilarg.currencybot.dto.ParsedCityDto;
import com.neilarg.currencybot.dto.ParsedCurrencyDto;
import com.neilarg.currencybot.dto.ParsedRegionDto;
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

        @Value("${xpath.region_div}")
        private String regionXpath;

        @Value("${xpath.city}")
        private String cityXpath;

        @Value("${xpath.region_name}")
        private String regionNameXpath;

        private final CrawlerService crawlerService;

        @Autowired
        public ParsingService(CrawlerService crawlerService) {
                this.crawlerService = crawlerService;
        }

        public List<ParsedCurrencyDto> parseCurrencies() throws Exception {
                List<ParsedCurrencyDto> parsedCurrencies = new ArrayList<>();
                crawlerService.loadCityPage("minsk").selectXpath(currencyRowXpath)
                        .forEach(row -> parsedCurrencies.addAll(parseCurrencyRow(row)));
                return parsedCurrencies;
        }

        private List<ParsedCurrencyDto> parseCurrencyRow(Element row) {
                Elements columns = row.selectXpath(".//td");
                ParsedCurrencyDto usd = new ParsedCurrencyDto(columns.get(0).text(),
                                "USD",
                                Double.parseDouble(columns.get(1).text()),
                                Double.parseDouble(columns.get(2).text()));
                ParsedCurrencyDto eur = new ParsedCurrencyDto(columns.get(0).text(),
                                "EUR",
                                Double.parseDouble(columns.get(3).text()),
                                Double.parseDouble(columns.get(4).text()));
                ParsedCurrencyDto rub = new ParsedCurrencyDto(columns.get(0).text(),
                                "RUB",
                                Double.parseDouble(columns.get(5).text()),
                                Double.parseDouble(columns.get(6).text()));
                return List.of(usd, eur, rub);
        }

        public List<ParsedRegionDto> parseRegions() throws Exception{
                return crawlerService.loadCityPage("minsk").selectXpath(regionXpath)
                        .stream().map(this::parseRegionBlock).toList();
        }

        private ParsedRegionDto parseRegionBlock(Element block) {
                return new ParsedRegionDto(block.selectXpath(regionNameXpath).text(),
                        block.selectXpath(cityXpath).stream().map(this::parseCityRow).toList());
        }

        private ParsedCityDto parseCityRow(Element row) {
                return new ParsedCityDto(row.text(),
                        row.attr("data-slug"));
        }

}
