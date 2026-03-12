package com.doyoucode.universal_pet_car.service.veterinarian;

import com.doyoucode.universal_pet_car.dto.UserDto;
import com.doyoucode.universal_pet_car.entity.Veterinarian;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IVeterinarianService {
    List<UserDto> getAllVeteriansWithDetails();

    List<UserDto> findAvailableVetsForAppointment(String specialization, LocalDate date, LocalTime time);

    // Check all veterinarians
    List<Veterinarian> getVeterinariansBySpecialization(String specialization);
}
