package com.neilarg.currencybot.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CrawlerService {

    @Value("#{${headers}}")
    private Map<String, String> headers;

    @Value("${currency_url}")
    private String currencyUrl;

    @Value("${proxy.ip}")
    private String proxyIp;

    @Value("${proxy.port}")
    private Integer proxyPort;

    public Document loadCityPage(String city) throws Exception {
        return Jsoup.connect(currencyUrl.concat(city))
                .headers(headers)
                .proxy(proxyIp, proxyPort)
                .timeout(60000)
                .get();
    }

}
