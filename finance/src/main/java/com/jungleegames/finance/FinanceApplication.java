package com.jungleegames.finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.jungleegames.finance.commons.FileStorageProperty;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageProperty.class })
public class FinanceApplication {
	public static void main(String[] args) {
		SpringApplication.run(FinanceApplication.class, args);
	}

}
