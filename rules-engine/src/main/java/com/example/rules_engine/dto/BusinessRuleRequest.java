package com.example.rules_engine.dto;

import java.util.List;

public class BusinessRuleRequest {
    private String customerId;
    private List<String> ruleNames;

    public BusinessRuleRequest(String customerId, List<String> ruleNames) {
        this.customerId = customerId;
        this.ruleNames = ruleNames;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<String> getRuleNames() {
        return ruleNames;
    }

    public void setRuleNames(List<String> ruleNames) {
        this.ruleNames = ruleNames;
    }
}
