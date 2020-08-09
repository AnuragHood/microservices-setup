package edu.spring.microservices.limitsservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import edu.spring.microservices.limitsservice.bean.LimitConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class LimitsConfigurationController {
    @Autowired
    private Configuration configuration;
    @GetMapping("/limits")
    public LimitConfiguration retreiveLimitConfigs(){
        return new LimitConfiguration
                (configuration.getMaximum(),configuration.getMinimum());

    }
    @GetMapping("/fault-tolerant-limit")
    @HystrixCommand(fallbackMethod = "fallbackRetreiveLimitConfig")
    public LimitConfiguration retreiveLimitConfig(){
        throw new RuntimeException("Not available");

    }
    public LimitConfiguration fallbackRetreiveLimitConfig(){
        return new LimitConfiguration(999,9);
    }
}
