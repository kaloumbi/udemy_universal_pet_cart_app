package com.doyoucode.universal_pet_car.service.veterinarian;

import com.doyoucode.universal_pet_car.dto.UserDto;

import java.util.List;

public interface IVeterinarianService {
    List<UserDto> getAllVeteriansWithDetails();
}
