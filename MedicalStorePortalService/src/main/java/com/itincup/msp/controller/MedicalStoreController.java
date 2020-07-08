package com.itincup.msp.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

//@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.DELETE, RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@CrossOrigin(origins = "https://selfcare-hospital-management-system.cfapps.io/", methods = {RequestMethod.DELETE, RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RestController
@RequestMapping(value = "/medicine-inventory", produces = { MediaType.APPLICATION_JSON_VALUE })
public class MedicalStoreController {

	private static final Logger LOGGER = LogManager.getLogger(MedicalStoreController.class);
	
	@Autowired
	private MedicineRepository repository;
	@Autowired
	private IMedicineStore iMedicine;

	@GetMapping("/medicine")
	public ResponseEntity<List<Medicine>> getAllMedicine() {
		LOGGER.info("inside GetAllMedicine()");
		return iMedicine.getAllMedicine();
	}

	@PostMapping("/medicine")
	ResponseEntity<Medicine> saveMedicine(@RequestBody Medicine newMedicine) {
		LOGGER.info("inside saveMedine() - START : ",newMedicine);
		ResponseEntity<Medicine> entityResponse;
		Medicine medicine = null;
		try {
			medicine = iMedicine.saveMedicine(newMedicine);
			entityResponse = new ResponseEntity<>(medicine, HttpStatus.CREATED);
		} catch (Exception e) {
			LOGGER.error("ERROR : inside saveMedicine : ",e);
			entityResponse = new ResponseEntity<>(medicine, HttpStatus.EXPECTATION_FAILED);
		}
		LOGGER.info("inside saveMedine() - END ",newMedicine);
		return entityResponse;
	}

	@GetMapping("/medicine/{medicineName}")
	public ResponseEntity<Medicine> getMedicineByName(@PathVariable("medicineName") String medicineName) {
		LOGGER.info("inside getMedicineByName() : - START : ",medicineName);
		return iMedicine.getMedicineByName(medicineName);
	}

	@PutMapping("/medicine/{medicineName}")
	public ResponseEntity<Medicine> updateMedicine(@PathVariable("medicineName") String medicineName,
			@RequestBody Medicine newMedicine) {
		LOGGER.info("inside updateMedicine() : - START : ",medicineName +" - "+newMedicine);
		return iMedicine.updateMedicine(medicineName, newMedicine);
	}

	@Transactional
	@DeleteMapping("/medicine/{medicineName}")
	public ResponseEntity<HttpStatus> deleteMedicine(@PathVariable("medicineName") String medicineName) {
		LOGGER.info("inside deleteMedicine() START : ", medicineName);
		return iMedicine.deleteMedicine(medicineName);
	}

	@DeleteMapping("/medicine")
	public ResponseEntity<HttpStatus> deleteAllMedicine() {
		LOGGER.info("inside deleteAllMedicine() START : ");
		try {
			repository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

}