package com.doyoucode.universal_pet_car.controller;


import com.doyoucode.universal_pet_car.dto.UserDto;
import com.doyoucode.universal_pet_car.entity.Veterinarian;
import com.doyoucode.universal_pet_car.response.ApiResponse;
import com.doyoucode.universal_pet_car.service.veterinarian.IVeterinarianService;
import com.doyoucode.universal_pet_car.utils.FeedBackMessage;
import com.doyoucode.universal_pet_car.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(UrlMapping.VETERINARIANS)
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
public class VeterianController {

    private final IVeterinarianService veterinarianService;


    @GetMapping(UrlMapping.GET_ALL_VETERINARIANS)
    public ResponseEntity<ApiResponse> getAllVeterinarians(){

        List<UserDto> allVeterinarians = veterinarianService.getAllVeteriansWithDetails();

        return ResponseEntity.ok(new ApiResponse(FeedBackMessage.RESOURCE_FOUND, allVeterinarians));
    }

    @GetMapping(UrlMapping.SEARCH_VETERINARIAN_FOR_APPOINTMENT)
    public ResponseEntity<ApiResponse> searchVeterinarians(
            @RequestParam String specialization,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) LocalTime time){

        List<UserDto> availableVeterinarians = veterinarianService.findAvailableVetsForAppointment(specialization, date, time);
        if (availableVeterinarians.isEmpty()){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(FeedBackMessage.RESOURCE_NOT_FOUND, null));
        }
        return ResponseEntity.ok(new ApiResponse(FeedBackMessage.RESOURCE_FOUND, availableVeterinarians));
    }

}
