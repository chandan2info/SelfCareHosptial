package com.itincup.msp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

@RestController
@RequestMapping(value = "/medicine-inventory", produces = { MediaType.APPLICATION_JSON_VALUE })
public class MedicalStoreController {
	
	@Autowired
	private MedicineRepository repository;	
 
    @GetMapping(value = "/medicine")
    public List<Medicine> getAllMedicine() {
        return repository.findAll();
    }
 
    @PostMapping("/medicine")
    Medicine createOrSaveMedicine(@RequestBody Medicine newMedicine) {
        return repository.save(newMedicine);
    }
 
    @GetMapping("/medicine/{id}")
    Medicine getMedicineById(@PathVariable Long id) {
        return repository.findById(id).get();
    }
 
    @PutMapping("/medicine/{id}")
    Medicine updateMedicine(@RequestBody Medicine newMedicine, @PathVariable Long id) {
 
        return repository.findById(id).map(medicine -> {
        	medicine.setId(newMedicine.getId());
        	medicine.setMedicine_name(newMedicine.getMedicine_name());
        	medicine.setQuantity(newMedicine.getQuantity());
        	medicine.setPrice(newMedicine.getPrice());
        	medicine.setSell_med_qantity(newMedicine.getSell_med_qantity());
        	medicine.setAvail_med_quantity(newMedicine.getAvail_med_quantity());
            return repository.save(medicine);
        }).orElseGet(() -> {
        	newMedicine.setId(id);
            return repository.save(newMedicine);
        });
    }
 
    @DeleteMapping("/medicine/{id}")
    void deleteMedicine(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
