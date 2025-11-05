package com.doyoucode.universal_pet_car.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String gender;

    private String phoneNumber;

    private String email;

    private String userType;

    private boolean isEnabled;

    private String specialization;

}
