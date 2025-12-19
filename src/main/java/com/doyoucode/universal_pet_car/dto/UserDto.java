package com.doyoucode.universal_pet_car.dto;

import com.doyoucode.universal_pet_car.entity.Photo;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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

    private LocalDate createAt;

    private List<AppointmentDto> appointments = new ArrayList<>();

    private Photo photo;

    private List<ReviewDto> reviews = new ArrayList<>();

    private long imageId;

    private byte[] images;

    private double averageRating;

}
