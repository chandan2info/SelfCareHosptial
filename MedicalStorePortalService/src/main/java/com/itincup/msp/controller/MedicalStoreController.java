package com.itincup.msp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itincup.msp.entity.Medicine;
import com.itincup.msp.repository.MedicineRepository;
import com.itincup.msp.service.IMedicineStore;

@RestController
@RequestMapping(value = "/medicine-inventory", produces = { MediaType.APPLICATION_JSON_VALUE })
public class MedicalStoreController {

	@Autowired
	private MedicineRepository repository;

	@Autowired
	private IMedicineStore iMedicine;

	@GetMapping("/medicine")
	public ResponseEntity<List<Medicine>> getAllMedicine() {
		List<Medicine> response = null;
		ResponseEntity<List<Medicine>> entityResponse;
		try {
			response = repository.findAll();

			if (response == null || response.isEmpty()) {
				entityResponse = new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
			} else {
				entityResponse = new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			entityResponse = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return entityResponse;
	}

	@PostMapping("/medicine")
	ResponseEntity<Medicine> createOrSaveMedicine(@RequestBody Medicine newMedicine) {

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

		ResponseEntity<Medicine> entityResponse;
		Optional<Medicine> medicineResponse = null;
		try {
			medicineResponse = repository.findByMedicineName(medicineName);

			if (medicineResponse.isPresent()) {

				entityResponse = new ResponseEntity<>(medicineResponse.get(), HttpStatus.OK);
			} else {
				entityResponse = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			entityResponse = new ResponseEntity<>(medicineResponse.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return entityResponse;
	}

	@PutMapping("/medicine/{medicineName}")
	public ResponseEntity<Medicine> updateTutorial(@PathVariable("medicineName") String medicineName,
			@RequestBody Medicine newMedicine) {

		ResponseEntity<Medicine> entityResponse;
		Optional<Medicine> medicineResponse = repository.findByMedicineName(medicineName);

		if (medicineResponse.isPresent()) {

			Medicine medicine = medicineResponse.get();
			medicine.setMedicineName(medicine.getMedicineName());
			/*
			 * if(newMedicine.getMedicineName()==null) {
			 * medicine.setMedicineName(medicine.getMedicineName()); }else {
			 * medicine.setMedicineName(newMedicine.getMedicineName()); }
			 */
			if (newMedicine.getQuantity() == null) {
				medicine.setQuantity(medicine.getQuantity());
			} else {
				medicine.setQuantity(newMedicine.getQuantity());
			}
			if (newMedicine.getPrice() == null) {
				medicine.setPrice(medicine.getPrice());
			} else {
				medicine.setPrice(newMedicine.getPrice());
			}
			if (newMedicine.getSell_med_qantity() == null) {
				medicine.setSell_med_qantity(medicine.getSell_med_qantity());
			} else {
				medicine.setSell_med_qantity(newMedicine.getSell_med_qantity());
			}
			if (newMedicine.getAvail_med_quantity() == null) {
				medicine.setAvail_med_quantity(medicine.getAvail_med_quantity());
			} else {
				medicine.setAvail_med_quantity(newMedicine.getAvail_med_quantity());
			}
			Medicine updatedResponse = repository.save(medicine);
			entityResponse = new ResponseEntity<>(updatedResponse, HttpStatus.OK);
		} else {
			entityResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return entityResponse;
	}

	@DeleteMapping("/medicine/{medicineName}")
	public ResponseEntity<HttpStatus> deleteMedicine(@PathVariable("medicineName") String medicineName) {
		try {
			repository.deleteByMedicineName(medicineName);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
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