package com.itincup.msp.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.itincup.msp.entity.Medicine;

public interface IMedicineStore {
	
	public Medicine saveMedicine(Medicine newMedicine);

	public ResponseEntity<List<Medicine>> getAllMedicine();
	public ResponseEntity<Medicine> getMedicineByName(String medicineName);
	public ResponseEntity<Medicine> updateMedicine( String medicineName, Medicine newMedicine);
	public ResponseEntity<HttpStatus> deleteMedicine(String medicineName);
}
