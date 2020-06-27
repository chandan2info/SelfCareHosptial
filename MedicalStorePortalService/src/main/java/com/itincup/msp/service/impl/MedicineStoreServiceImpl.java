package com.itincup.msp.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itincup.msp.entity.Medicine;
import com.itincup.msp.repository.MedicineRepository;
import com.itincup.msp.service.IMedicineStore;

@Service
public class MedicineStoreServiceImpl implements IMedicineStore{

	private String fileLocation="E:/insertQueryScript.txt";
	private String fileLoc = "src/main/resources/data.sql";
	
	@Autowired
	private MedicineRepository repository;
	
	@Override
	public Medicine saveMedicine(Medicine medicine) {
		buildInsertSqlQueryScript(medicine);
		return repository.save(medicine);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
