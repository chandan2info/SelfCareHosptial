package com.itincup.msp.controller;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itincup.msp.entity.Medicine;
import com.itincup.msp.repository.MedicineRepository;
import com.itincup.msp.service.IMedicineStore;

@CrossOrigin(origins = "https://selfcare-hospital-management-system.cfapps.io/", methods = {RequestMethod.DELETE, RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RestController
@RequestMapping(value = "/medicine-inventory", produces = { MediaType.APPLICATION_JSON_VALUE })
public class MedicalStoreController {

	@Autowired
	private MedicineRepository repository;
	@Autowired
	private IMedicineStore iMedicine;

	@GetMapping("/medicine")
	public ResponseEntity<List<Medicine>> getAllMedicine() {
		return iMedicine.getAllMedicine();
	}

	@PostMapping("/medicine")
	ResponseEntity<Medicine> saveMedicine(@RequestBody Medicine newMedicine) {
		ResponseEntity<Medicine> entityResponse;
		Medicine medicine = null;
		try {
			medicine = iMedicine.saveMedicine(newMedicine);
			entityResponse = new ResponseEntity<>(medicine, HttpStatus.CREATED);
		} catch (Exception e) {
			entityResponse = new ResponseEntity<>(medicine, HttpStatus.EXPECTATION_FAILED);
		}
		return entityResponse;
	}

	@GetMapping("/medicine/{medicineName}")
	public ResponseEntity<Medicine> getMedicineByName(@PathVariable("medicineName") String medicineName) {
		return iMedicine.getMedicineByName(medicineName);
	}

	@PutMapping("/medicine/{medicineName}")
	public ResponseEntity<Medicine> updateMedicine(@PathVariable("medicineName") String medicineName,
			@RequestBody Medicine newMedicine) {
		return iMedicine.updateMedicine(medicineName, newMedicine);
	}

	@Transactional
	@DeleteMapping("/medicine/{medicineName}")
	public ResponseEntity<HttpStatus> deleteMedicine(@PathVariable("medicineName") String medicineName) {
		return iMedicine.deleteMedicine(medicineName);
	}

	@DeleteMapping("/medicine")
	public ResponseEntity<HttpStatus> deleteAllMedicine() {
		try {
			repository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

}