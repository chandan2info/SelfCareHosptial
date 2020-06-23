package com.itincup.msp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalStoreController {
	
	@GetMapping("/name")
	public String getMedicalStoreName() {
		return "Apna Medical Store";
	}

}
