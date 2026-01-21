package com.doyoucode.universal_pet_car.controller;


import com.doyoucode.universal_pet_car.dto.UserDto;
import com.doyoucode.universal_pet_car.response.ApiResponse;
import com.doyoucode.universal_pet_car.service.veterinarian.IVeterinarianService;
import com.doyoucode.universal_pet_car.utils.FeedBackMessage;
import com.doyoucode.universal_pet_car.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(UrlMapping.VETERINARIANS)
@RequiredArgsConstructor
public class VeterianController {

    private final IVeterinarianService veterinarianService;


    @GetMapping(UrlMapping.GET_ALL_VETERINARIANS)
    public ResponseEntity<ApiResponse> getAllVeterinarians(){

        List<UserDto> allVeterinarians = veterinarianService.getAllVeteriansWithDetails();

        return ResponseEntity.ok(new ApiResponse(FeedBackMessage.RESOURCE_FOUND, allVeterinarians));
    }

}
