package com.perficient.expressions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ExpressionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpressionsApplication.class, args);
	}

}
//cambio para pruebas