package com.example.rules_engine.core;

import com.example.rules_engine.exception.BusinessRuleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditLimitRuleTest {

    private CreditLimitRule creditLimitRule;

    // Mock the Random class to control its behavior
    private final Random mockRandom = Mockito.mock(Random.class);

    @BeforeEach
    void setUp() {
        // Inject the mocked Random instance into the CreditLimitRule
        creditLimitRule = new CreditLimitRule() {
            @Override
            public Random getRandomInstance() {
                return mockRandom; // Use the mocked Random instance
            }
        };
    }

    @Test
    void testExecute_SuccessfulExecution() throws BusinessRuleException {
        // Arrange
        String customerId = "CUST123";
        when(mockRandom.nextInt(10)).thenReturn(5); // Simulate a value <= 7 (success case)

        // Act
        creditLimitRule.execute(customerId);

        // Assert
        // No exception should be thrown
    }

    @Test
    void testExecute_FailureDueToException() {
        // Arrange
        String customerId = "CUST456";
        when(mockRandom.nextInt(10)).thenReturn(8); // Simulate a value > 7 (failure case)

        // Act & Assert
        BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> {
            creditLimitRule.execute(customerId);
        });

        // Verify the exception message
        assertEquals("Credit limit exceeded for customer ID: " + customerId, exception.getMessage());
    }

    @Test
    void testGetRuleName() {
        // Act
        String ruleName = creditLimitRule.getRuleName();

        // Assert
        assertEquals("CreditLimitRule", ruleName);
    }
}