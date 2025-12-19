package com.doyoucode.universal_pet_car.dto;

import com.doyoucode.universal_pet_car.entity.Appointment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetDto {
    private Long id;

    private String name;

    private String type;

    private String breed;

    private int age;

    private String color;

}
