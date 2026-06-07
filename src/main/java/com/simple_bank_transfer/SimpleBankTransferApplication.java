package com.simple_bank_transfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimpleBankTransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleBankTransferApplication.class, args);
	}

}
