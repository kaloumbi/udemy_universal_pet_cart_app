package com.doyoucode.universal_pet_car.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointementUpdateRequest {

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private String reason;

}
