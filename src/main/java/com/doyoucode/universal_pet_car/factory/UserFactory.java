package com.doyoucode.universal_pet_car.factory;

import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.request.RegistrationRequest;

public interface UserFactory {

    public User createUser(RegistrationRequest registrationRequest);

}
