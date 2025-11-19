package com.doyoucode.universal_pet_car.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentDto {

    private String reason;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private String appointmentNo;

    private LocalDate createAt;
}
