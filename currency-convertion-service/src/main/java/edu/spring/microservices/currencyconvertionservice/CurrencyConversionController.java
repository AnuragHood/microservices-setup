package edu.spring.microservices.currencyconvertionservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {
    private static Logger LOG = LoggerFactory.getLogger(CurrencyConversionController.class);
    @Autowired
    private CurrencyConvertorProxy currencyConvertorProxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversionBean> responseEntity =
                new RestTemplate().getForEntity("http://localhost:8001/currency-exchange/from/{from}/to/{to}",
                        CurrencyConversionBean.class, uriVariables);
        return new CurrencyConversionBean(responseEntity.getBody().getId(), from, to,
                responseEntity.getBody().getconversionMultiple(), quantity, quantity.multiply(responseEntity.getBody().getconversionMultiple()),
                responseEntity.getBody().getPort());

    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        CurrencyConversionBean responseEntity = currencyConvertorProxy.retrieveExchangeValue(from, to);
        LOG.info("{}", responseEntity);
        return new CurrencyConversionBean(responseEntity.getId(), from, to,
                responseEntity.getconversionMultiple(), quantity, quantity.multiply(responseEntity.getconversionMultiple()),
                responseEntity.getPort());

    }
}
