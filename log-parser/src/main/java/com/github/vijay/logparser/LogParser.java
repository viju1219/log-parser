package com.github.vijay.logparser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.vijay.logparser.service.LogParserService;

@SpringBootApplication
public class LogParser implements CommandLineRunner {

	@Autowired
	LogParserService service;

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(LogParser.class);
		app.run(args);
	}

	public void run(String... args) throws Exception {
		service.process(args);
	}

}
