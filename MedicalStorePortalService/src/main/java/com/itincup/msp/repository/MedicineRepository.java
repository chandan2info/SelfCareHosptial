package com.itincup.msp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itincup.msp.entity.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

}
