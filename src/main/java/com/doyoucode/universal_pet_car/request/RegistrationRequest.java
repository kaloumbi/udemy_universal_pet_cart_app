package com.doyoucode.universal_pet_car.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {

    private Long id;

    private String firstName;

    private String lastName;

    private String gender;

    @Column(name = "mobile")
    private String phoneNumber;

    private String email;

    private String password;

    private String userType;

    private boolean isEnabled;

    private String specialization;
}
