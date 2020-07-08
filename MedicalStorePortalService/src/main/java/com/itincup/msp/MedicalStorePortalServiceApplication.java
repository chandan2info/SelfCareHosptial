package com.itincup.msp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class MedicalStorePortalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalStorePortalServiceApplication.class, args);
	}

}
