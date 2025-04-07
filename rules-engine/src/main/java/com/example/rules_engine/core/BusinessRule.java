package com.example.rules_engine.core;



import com.example.rules_engine.exception.BusinessRuleException;

import java.util.Random;

public interface BusinessRule {
    void execute(String customerId) throws BusinessRuleException;
    String getRuleName();

    Random getRandomInstance();
}
