package com.macgarcia.documento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions = false)
public class ApiDocumentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDocumentoApplication.class, args);
	}

}
