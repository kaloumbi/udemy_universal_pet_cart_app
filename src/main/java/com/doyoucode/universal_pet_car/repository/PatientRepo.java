package com.doyoucode.universal_pet_car.repository;

import com.doyoucode.universal_pet_car.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient, Long> {
}
