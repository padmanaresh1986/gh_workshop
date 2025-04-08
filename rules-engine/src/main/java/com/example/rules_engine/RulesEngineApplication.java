package com.example.rules_engine;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RulesEngineApplication implements  CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(RulesEngineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String application_name = "Rule Engine";
		System.out.println("should not use sys outs , use loggers instead");
	}
}
