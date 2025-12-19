package com.doyoucode.universal_pet_car.dto;


import com.doyoucode.universal_pet_car.entity.Pet;
import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AppointmentDto {

    private Long id;

    private String reason;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private String appointmentNo;

    private LocalDate createAt;

    private AppointmentStatus appointmentStatus;

    private UserDto patient;

    private UserDto veterinarian;

    private List<PetDto> pets = new ArrayList<>();

//    private String reason;
//
//    private LocalDate appointmentDate;
//
//    private LocalTime appointmentTime;
//
//    private String appointmentNo;
//
//    private LocalDate createAt;
}
