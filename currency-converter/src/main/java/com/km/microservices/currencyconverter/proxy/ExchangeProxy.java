package com.km.microservices.currencyconverter.proxy;

import com.km.microservices.currencyconverter.beans.CurrencyConverterBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@FeignClient(name = "currency-exchange-service")
public interface ExchangeProxy {

    @RequestMapping("/currency-exchange/from/{from}/to/{to}")
    Optional<CurrencyConverterBean> convert(@PathVariable String from, @PathVariable String to);
}
