package com.doyoucode.universal_pet_car.repository;

import com.doyoucode.universal_pet_car.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepo extends JpaRepository<Pet, Long> {

}
