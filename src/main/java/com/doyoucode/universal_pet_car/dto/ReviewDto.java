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
    private Long vetId;

    private String vetName;

    private Long reviewerId;

    private String reviewerName;

    private byte[] reviewerImage;

    private byte[] vetImage;

}
