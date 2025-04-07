package com.example.rules_engine.controller;

import com.example.rules_engine.core.BusinessRule;
import com.example.rules_engine.dto.BusinessRuleRequest;
import com.example.rules_engine.dto.BusinessRuleResponse;
import com.example.rules_engine.exception.BusinessRuleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BusinessRuleControllerTest {

    private BusinessRule mockRule1;
    private BusinessRule mockRule2;
    private BusinessRuleController controller;

    @BeforeEach
    void setUp() {
        // Mock BusinessRule implementations
        mockRule1 = mock(BusinessRule.class);
        when(mockRule1.getRuleName()).thenReturn("Rule1");

        mockRule2 = mock(BusinessRule.class);
        when(mockRule2.getRuleName()).thenReturn("Rule2");

        // Initialize the controller with mocked rules
        List<BusinessRule> businessRules = Arrays.asList(mockRule1, mockRule2);
        controller = new BusinessRuleController(businessRules);
    }

    @Test
    void testExecuteRules_SuccessfulExecution() throws BusinessRuleException {
        // Arrange
        String customerId = "CUST123";
        List<String> ruleNames = Arrays.asList("Rule1", "Rule2");
        BusinessRuleRequest request = new BusinessRuleRequest(customerId, ruleNames);

        // Mock rule execution
        doNothing().when(mockRule1).execute(customerId);
        doNothing().when(mockRule2).execute(customerId);

        // Act
        BusinessRuleResponse response = controller.executeRules(request);

        // Assert
        assertThat(response.getCustomerId()).isEqualTo(customerId);
        assertThat(response.getResults()).containsExactly(
                "✅ Rule [Rule1] passed for customer ID: CUST123",
                "✅ Rule [Rule2] passed for customer ID: CUST123"
        );

        // Verify interactions
        verify(mockRule1).execute(customerId);
        verify(mockRule2).execute(customerId);
    }

    @Test
    void testExecuteRules_RuleThrowsException() throws  BusinessRuleException{
        // Arrange
        String customerId = "CUST456";
        List<String> ruleNames = Collections.singletonList("Rule1");
        BusinessRuleRequest request = new BusinessRuleRequest(customerId, ruleNames);

        // Mock rule execution to throw exception
        doThrow(new BusinessRuleException("Validation failed")).when(mockRule1).execute(customerId);

        // Act
        BusinessRuleResponse response = controller.executeRules(request);

        // Assert
        assertThat(response.getCustomerId()).isEqualTo(customerId);
        assertThat(response.getResults()).containsExactly(
                "❌ Rule [Rule1] failed: Validation failed"
        );

        // Verify interaction
        verify(mockRule1).execute(customerId);
    }

    @Test
    void testExecuteRules_RuleNotFound() {
        // Arrange
        String customerId = "CUST789";
        List<String> ruleNames = Collections.singletonList("NonExistentRule");
        BusinessRuleRequest request = new BusinessRuleRequest(customerId, ruleNames);

        // Act
        BusinessRuleResponse response = controller.executeRules(request);

        // Assert
        assertThat(response.getCustomerId()).isEqualTo(customerId);
        assertThat(response.getResults()).containsExactly(
                "⚠️ Rule [NonExistentRule] not found."
        );
    }

    @Test
    void testExecuteRules_EmptyRuleNames() {
        // Arrange
        String customerId = "CUST000";
        List<String> ruleNames = Collections.emptyList();
        BusinessRuleRequest request = new BusinessRuleRequest(customerId, ruleNames);

        // Act
        BusinessRuleResponse response = controller.executeRules(request);

        // Assert
        assertThat(response.getCustomerId()).isEqualTo(customerId);
        assertThat(response.getResults()).isEmpty();
    }

    @Test
    void testExecuteRules_NullCustomerId() throws BusinessRuleException{
        // Arrange
        String customerId = null;
        List<String> ruleNames = Collections.singletonList("Rule1");
        BusinessRuleRequest request = new BusinessRuleRequest(customerId, ruleNames);

        // Mock rule execution
        doNothing().when(mockRule1).execute(customerId);

        // Act
        BusinessRuleResponse response = controller.executeRules(request);

        // Assert
        assertThat(response.getCustomerId()).isNull();
        assertThat(response.getResults()).containsExactly(
                "✅ Rule [Rule1] passed for customer ID: null"
        );

        // Verify interaction
        verify(mockRule1).execute(customerId);
    }
}