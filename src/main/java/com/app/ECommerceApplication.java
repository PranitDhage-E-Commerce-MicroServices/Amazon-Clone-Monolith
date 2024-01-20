package com.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;

@EnableAutoConfiguration
@EnableJpaRepositories("com.app.pojo")
@Slf4j
public class ECommerceApplication {

	public static void main(String[] args) {

		Long start = LocalDate.now().toEpochDay();
		SpringApplication.run(ECommerceApplication.class, args);

		Long end = LocalDate.now().toEpochDay();
		log.info("Application Started Successfully in {} seconds", end - start);
	}
}
