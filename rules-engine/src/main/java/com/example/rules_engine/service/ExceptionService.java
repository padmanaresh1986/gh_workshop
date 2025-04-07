package com.example.rules_engine.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ExceptionService {

    private static final Logger log = LoggerFactory.getLogger(ExceptionService.class);
    private final Random random = new Random();

    @Scheduled(fixedRate = 1000000) // Run every 10 seconds
    public void generateRandomExceptions() {
        int exceptionType = random.nextInt(5);

        try {
            switch (exceptionType) {
                case 0:
                    throw new NullPointerException("Simulated NullPointerException");
                case 1:
                    throw new IllegalArgumentException("Simulated IllegalArgumentException");
                case 2:
                    throw new ArithmeticException("Simulated ArithmeticException");
                case 3:
                    throw new ArrayIndexOutOfBoundsException("Simulated ArrayIndexOutOfBoundsException");
                case 4:
                    throw new RuntimeException("Simulated Generic RuntimeException");
                default:
                    log.info("No exception this time!");
            }
        } catch (Exception e) {
            log.error("Exception occurred: ", e);
        }
    }
}