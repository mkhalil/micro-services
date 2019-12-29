package com.km.microservices.currencyconverter.controller;

import com.km.microservices.currencyconverter.beans.CurrencyConverterBean;
import com.km.microservices.currencyconverter.config.ConfigServer;
import com.km.microservices.currencyconverter.proxy.ExchangeProxy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
public class CurrencyConverterController {

    private final ConfigServer configServer;

    private final
    ExchangeProxy exchangeProxy;

    public CurrencyConverterController(ExchangeProxy exchangeProxy, ConfigServer configServer) {
        this.exchangeProxy = exchangeProxy;
        this.configServer = configServer;
    }

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConverterBean convert(@PathVariable("from") String from, @PathVariable("to") String to, @PathVariable BigDecimal quantity) {
        return this.exchangeProxy.convert(from, to).map(currencyConverterBean -> {
            currencyConverterBean.setQuantity(quantity);
            currencyConverterBean.setTotalCalculatedAmount(currencyConverterBean.getConversionMultiple().multiply(quantity));
            return currencyConverterBean;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                "Bad request, Currency converter cannot be calculated"));
    }
}
