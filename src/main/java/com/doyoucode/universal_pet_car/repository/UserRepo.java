package com.doyoucode.universal_pet_car.repository;

import com.doyoucode.universal_pet_car.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}
