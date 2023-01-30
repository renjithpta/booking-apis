package com.ibs.iairport.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class IAirportApplication {

	public static void main(String[] args) {
		SpringApplication.run(IAirportApplication.class, args);
	}

}
