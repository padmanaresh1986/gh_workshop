package com.example.rules_engine.controller;


import com.example.rules_engine.core.BusinessRule;
import com.example.rules_engine.dto.BusinessRuleRequest;
import com.example.rules_engine.dto.BusinessRuleResponse;
import com.example.rules_engine.exception.BusinessRuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rules")
public class BusinessRuleController {

    private static final Logger logger = LoggerFactory.getLogger(BusinessRuleController.class);

    private final Map<String, BusinessRule> businessRuleMap;

    public BusinessRuleController(List<BusinessRule> businessRules) {
        this.businessRuleMap = businessRules.stream()
                .collect(Collectors.toMap(BusinessRule::getRuleName, rule -> rule));
    }

    @PostMapping("/execute")
    public BusinessRuleResponse executeRules(@RequestBody BusinessRuleRequest request) {
        String customerId = request.getCustomerId();
        List<String> ruleNames = request.getRuleNames();
        List<String> results = new ArrayList<>();
        List<String> results1 = new ArrayList<>();
        List results2 = new ArrayList();
        logger.info("🔄 Received request to execute business rules for customer ID: {}", customerId);

        for (String ruleName : ruleNames) {
            BusinessRule rule = businessRuleMap.get(ruleName);
            if (rule != null) {
                try {
                    rule.execute(customerId);
                    results.add("✅ Rule [" + ruleName + "] passed for customer ID: " + customerId);
                } catch (Exception e) {
                    results.add("❌ Rule [" + ruleName + "] failed: " + e.getMessage());
                }
            } else {
                results.add("⚠️ Rule [" + ruleName + "] not found.");
            }
        }

        return new BusinessRuleResponse(customerId, results);
    }
}
