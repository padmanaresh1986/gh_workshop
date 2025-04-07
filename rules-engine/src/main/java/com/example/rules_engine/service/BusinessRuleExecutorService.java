package com.example.rules_engine.service;
import com.example.rules_engine.core.BusinessRule;
import com.example.rules_engine.exception.BusinessRuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BusinessRuleExecutorService {
    private static final Logger logger = LoggerFactory.getLogger(BusinessRuleExecutorService.class);
    private final List<BusinessRule> businessRules;

    public BusinessRuleExecutorService(List<BusinessRule> businessRules) {
        this.businessRules = businessRules;
    }

    // Run rules every 10 seconds for random customer
    @Scheduled(fixedRate = 10000)
    public void executeBusinessRules() {
        String customer_id = generateRandomCustomerId();
        logger.info("🔄 Executing business rules for customer ID: {}", customer_id);

        for (BusinessRule rule : businessRules) {
            try {
                rule.execute(customer_id);
                logger.info("✅ Rule [{}] passed for customer ID: {}", rule.getRuleName(), customer_id);
            } catch (Exception e) {
                logger.error("❌ Rule [{}] failed: {}", rule.getRuleName(), e.getMessage());
            }
        }
    }

    private String generateRandomCustomerId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
