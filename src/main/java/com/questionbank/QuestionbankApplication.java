package com.questionbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.questionbank")
public class QuestionbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestionbankApplication.class, args);
	}

}
