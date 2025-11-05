package com.doyoucode.universal_pet_car.service.user;


import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.request.RegistrationRequest;
import com.doyoucode.universal_pet_car.request.UserUpdateRequest;

public interface IUserService {

    User register(RegistrationRequest registrationRequest);

    User updateUser(Long userId, UserUpdateRequest request);
}
