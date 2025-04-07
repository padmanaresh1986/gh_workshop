package com.example.rules_engine.core;

import com.example.rules_engine.exception.BusinessRuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CreditLimitRule implements BusinessRule {
    private static final Logger logger = LoggerFactory.getLogger(CreditLimitRule.class);

    @Override
    public void execute(String customerId) throws BusinessRuleException {
        if (getRandomInstance().nextInt(10) > 7) { // Simulate failure for 30% of cases
            throw new BusinessRuleException("Credit limit exceeded for customer ID: " + customerId);
        }
        logger.info("✅ CreditLimitRule executed successfully for customer ID: {}", customerId);
    }

    @Override
    public String getRuleName() {
        return "CreditLimitRule";
    }

    @Override
    public Random getRandomInstance() {
        return  new Random();
    }
}