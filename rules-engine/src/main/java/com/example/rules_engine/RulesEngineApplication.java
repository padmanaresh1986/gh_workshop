package com.example.rules_engine;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RulesEngineApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RulesEngineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("******Application Initialized*****");
	}
}
