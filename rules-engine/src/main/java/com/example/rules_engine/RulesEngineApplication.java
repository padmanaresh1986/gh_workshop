package com.example.rules_engine;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RulesEngineApplication implements  ApplicationRunner{

	public static void main(String[] args) {
		SpringApplication.run(RulesEngineApplication.class, args);
	}

	public void run(String... args) throws Exception {
		System.out.println("should not use sys outs , use loggers");
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

	}
}
