package com.doyoucode.universal_pet_car.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdateRequest {

    private int stars;

    private String feedback;
}
