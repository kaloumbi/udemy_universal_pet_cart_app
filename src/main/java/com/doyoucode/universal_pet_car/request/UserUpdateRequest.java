package com.doyoucode.universal_pet_car.request;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {


    private String firstName;

    private String lastName;

    private String gender;

    private String phoneNumber;

    private String specialization;
}
