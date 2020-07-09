package com.itincup.msp.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.itincup.msp.controller.MedicalStoreController;
import com.itincup.msp.entity.Medicine;
import com.itincup.msp.repository.MedicineRepository;
import com.itincup.msp.service.IMedicineStore;

@Service
public class MedicineStoreServiceImpl implements IMedicineStore{

	private static final Logger LOGGER = LogManager.getLogger(MedicineStoreServiceImpl.class);
	
	private String fileLocation="E:/insertQueryScript.txt";
	private String fileLoc = "src/main/resources/data.sql";
	
	@Autowired
	private MedicineRepository repository;
	
	@Override
	public Medicine saveMedicine(Medicine medicine) {
		LOGGER.info("inside MedicineStoreServiceImpl : saveMedicine()");
		buildInsertSqlQueryScript(medicine);
		return repository.save(medicine);
	}
	
	 @Cacheable("medicine")
	public ResponseEntity<List<Medicine>> getAllMedicine() {
		LOGGER.info("inside MedicineStoreServiceImpl : getAllMedicine()");
		List<Medicine> response = null;
		ResponseEntity<List<Medicine>> entityResponse;
		try {
			response = repository.findAll();
			if (response == null || response.isEmpty()) {
				LOGGER.info("RESPONSE : inside MedicineStoreServiceImpl : getAllMedicine() - "+response);
				entityResponse = new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
			} else {
				LOGGER.info("RESPONSE : inside MedicineStoreServiceImpl : getAllMedicine() - "+ response);
				entityResponse = new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			LOGGER.error("EROR : inside MedicineStoreServiceImpl : getAllMedicine() - "+ e);
			entityResponse = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return entityResponse;
	}
	 @Cacheable("medicine-inventory")
	public ResponseEntity<Medicine> getMedicineByName(@PathVariable("medicineName") String medicineName) {
		LOGGER.info("inside MedicineStoreServiceImpl : getMedicineByName()");
		ResponseEntity<Medicine> entityResponse;
		Optional<Medicine> medicineResponse = null;
		try {
			medicineResponse = repository.findByMedicineName(medicineName);
			if (medicineResponse.isPresent()) {
				LOGGER.info("RESPONSE : inside MedicineStoreServiceImpl : getMedicineByName() - "+ medicineResponse);
				entityResponse = new ResponseEntity<>(medicineResponse.get(), HttpStatus.OK);
			} else {
				LOGGER.info("RESPONSE : inside MedicineStoreServiceImpl : getMedicineByName() - ", medicineResponse);
				entityResponse = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : inside MedicineStoreServiceImpl : getMedicineByName()"+ e);
			entityResponse = new ResponseEntity<>(medicineResponse.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return entityResponse;
	}

	 @Caching(evict = {
	            @CacheEvict(value="medicine-inventory", allEntries=true),
	            @CacheEvict(value="medicine", allEntries=true)})
	public ResponseEntity<Medicine> updateMedicine(@PathVariable("medicineName") String medicineName,
			@RequestBody Medicine newMedicine) {
		LOGGER.error("inside MedicineStoreServiceImpl : updateMedicine()");
		ResponseEntity<Medicine> entityResponse;
		Optional<Medicine> medicineResponse = repository.findByMedicineName(medicineName);
		if (medicineResponse.isPresent()) {
			Medicine medicine = medicineResponse.get();
			if (newMedicine.getMedicineName() == null) {
				medicine.setMedicineName(medicine.getMedicineName());
			} else {
				medicine.setMedicineName(newMedicine.getMedicineName());
			}
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
		LOGGER.info("RESPONSE : inside MedicineStoreServiceImpl : updateMedicine() - "+ entityResponse);
		return entityResponse;
	}
	
	public ResponseEntity<HttpStatus> deleteMedicine(@PathVariable("medicineName") String medicineName) {
		LOGGER.error("inside MedicineStoreServiceImpl : deleteMedicine()");
		try {
			repository.deleteByMedicineName(medicineName);
			return new ResponseEntity<>(HttpStatus.MOVED_PERMANENTLY);
		} catch (Exception e) {
			LOGGER.error("ERROR : inside MedicineStoreServiceImpl : deleteMedicine() "+ e);
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	private void buildInsertSqlQueryScript(Medicine medicine) {
		Path newFilePath = Paths.get(fileLoc);
		File file = new File(fileLoc);
	    try {
	    	if(!Files.exists(newFilePath)) {
	    		Files.createFile(newFilePath);
			}else {
				FileWriter fr = new FileWriter(file, true);
				BufferedWriter br = new BufferedWriter(fr);
				String query = "\nINSERT INTO MEDICINE_INVENTORY (medicine_name, quantity, price, sell_med_qantity, avail_med_quantity) VALUES ('"+medicine.getMedicineName()+"',"+medicine.getQuantity()+","+medicine.getPrice()+","+medicine.getSell_med_qantity()+","+medicine.getAvail_med_quantity()+");";
				br.write(query);
				br.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
