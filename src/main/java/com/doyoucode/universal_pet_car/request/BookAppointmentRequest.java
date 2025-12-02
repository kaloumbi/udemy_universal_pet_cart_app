package com.doyoucode.universal_pet_car.request;

import com.doyoucode.universal_pet_car.entity.Appointment;
import com.doyoucode.universal_pet_car.entity.Pet;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookAppointmentRequest {

    private Appointment appointment;

    private List<Pet> pets;
}
