package edu.spring.microservices.currencyexchangeservice.controller;

import edu.spring.microservices.currencyexchangeservice.bean.ExchangeValue;
import edu.spring.microservices.currencyexchangeservice.service.CurrencyExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class CurrencyConverterController {
    private Environment environment;
    private CurrencyExchangeService currencyExchangeService;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Autowired
    public void setCurrencyExchangeService(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }

    private static Logger LOG = LoggerFactory.getLogger(CurrencyConverterController.class);

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
        ExchangeValue value = currencyExchangeService.retreiveExchangeValue(from, to);
        value.setPort(Integer.parseInt(Objects.requireNonNull(environment.getProperty("local.server.port"))));
        LOG.info("{}", value.toString());
        return value;

    }
}
