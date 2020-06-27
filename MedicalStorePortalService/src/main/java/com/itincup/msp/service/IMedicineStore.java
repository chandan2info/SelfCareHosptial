package com.itincup.msp.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.itincup.msp.entity.Medicine;

public interface IMedicineStore {
	
	public Medicine saveMedicine(Medicine newMedicine);

}
