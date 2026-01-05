package com.doyoucode.universal_pet_car.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {

    private Long id;

    private String feeback;

    private int stars;

    //added after
    private Long veterinarianId;

    private String veterinarianName;

    private Long patientId;

    private String patientName;

    private byte[] patientImage;

    private byte[] veterinarianImage;

}
