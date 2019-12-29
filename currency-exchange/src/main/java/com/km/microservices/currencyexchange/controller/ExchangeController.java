package com.km.microservices.currencyexchange.controller;

import com.km.microservices.currencyexchange.config.ConfigServer;
import com.km.microservices.currencyexchange.repository.ExchangeRepository;
import com.km.microservices.currencyexchange.entities.Exchange;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ExchangeController {

    private final
    ExchangeRepository exchangeRepository;

    private final ConfigServer configServer;

    public ExchangeController(ExchangeRepository exchangeRepository, ConfigServer configServer) {
        this.exchangeRepository = exchangeRepository;
        this.configServer = configServer;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public Exchange convert(@PathVariable("from") String from, @PathVariable("to") String to) {
        Exchange  exchange = exchangeRepository.findByFromAndTo(from, to).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                "Bad request, Currency exchange not found"));
        exchange.setPort(configServer.getPort());
        return exchange;
    }
}
