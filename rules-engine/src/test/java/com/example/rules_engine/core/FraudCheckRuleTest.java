package com.example.rules_engine.core;

import com.example.rules_engine.exception.BusinessRuleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FraudCheckRuleTest {

    private FraudCheckRule fraudCheckRule;

    // Mock the Random class to control its behavior
    private final Random mockRandom = Mockito.mock(Random.class);

    @BeforeEach
    void setUp() {
        // Inject the mocked Random instance into the FraudCheckRule
        fraudCheckRule = new FraudCheckRule() {
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
        when(mockRandom.nextBoolean()).thenReturn(false); // Simulate success (no fraud detected)

        // Act
        fraudCheckRule.execute(customerId);

        // Assert
        // No exception should be thrown
    }

    @Test
    void testExecute_FailureDueToException() {
        // Arrange
        String customerId = "CUST456";
        when(mockRandom.nextBoolean()).thenReturn(true); // Simulate failure (fraud detected)

        // Act & Assert
        BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> {
            fraudCheckRule.execute(customerId);
        });

        // Verify the exception message
        assertEquals("Potential fraud detected for customer ID: " + customerId, exception.getMessage());
    }

    @Test
    void testGetRuleName() {
        // Act
        String ruleName = fraudCheckRule.getRuleName();

        // Assert
        assertEquals("FraudCheckRule", ruleName);
    }
}