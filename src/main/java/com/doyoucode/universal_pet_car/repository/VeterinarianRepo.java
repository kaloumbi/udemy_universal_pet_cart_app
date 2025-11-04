package com.doyoucode.universal_pet_car.repository;

import com.doyoucode.universal_pet_car.entity.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarianRepo extends JpaRepository<Veterinarian, Long> {
}
