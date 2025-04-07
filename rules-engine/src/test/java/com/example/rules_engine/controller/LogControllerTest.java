package com.example.rules_engine.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LogController.class)
class LogControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private LogController logController; // Inject the controller instance

    @BeforeEach
    void setUp() {
               // Initialize MockMvc with the controller
        mockMvc = MockMvcBuilders.standaloneSetup(logController).build();
    }

    @Test
    void testGenerateCustomLog_ValidMessage() throws Exception {
        String message = "This is a test log message";

        // Perform GET request
        mockMvc.perform(get("/generate-log")
                        .param("message", message))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(content().string("Log generated!")); // Expect specific response

   }

    @Test
    void testGenerateCustomLog_EmptyMessage() throws Exception {
        String message = "";

        // Perform GET request with an empty message
        mockMvc.perform(get("/generate-log")
                        .param("message", message))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(content().string("Log generated!")); // Expect specific response

    }

    @Test
    void testGenerateCustomLog_NullMessage() throws Exception {
        // Perform GET request without providing a message parameter
        mockMvc.perform(get("/generate-log"))
                .andExpect(status().isBadRequest()); // Expect HTTP 400 Bad Request
    }
}