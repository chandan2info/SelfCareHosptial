package com.itincup.msp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itincup.msp.entity.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, String> {

	@Query
	Optional<Medicine> findByMedicineName(@Param(value = "medicineName") String medicineName);

	@Query
	List<Medicine> deleteByMedicineName(@Param(value = "medicineName") String medicineName);

}
