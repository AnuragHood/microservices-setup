package edu.spring.microservices.currencyexchangeservice.service;

import edu.spring.microservices.currencyexchangeservice.bean.ExchangeValue;
import edu.spring.microservices.currencyexchangeservice.repo.ExChangeValueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyExchangeService {
    @Autowired
    private ExChangeValueRepo exChangeValueRepo;

    public ExchangeValue retreiveExchangeValue(String from, String to) {
        return exChangeValueRepo.findByFromAndTo(from, to);
    }
}
