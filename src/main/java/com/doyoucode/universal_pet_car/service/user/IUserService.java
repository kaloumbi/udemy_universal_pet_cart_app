package com.doyoucode.universal_pet_car.service.user;


import com.doyoucode.universal_pet_car.dto.UserDto;
import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.request.RegistrationRequest;
import com.doyoucode.universal_pet_car.request.UserUpdateRequest;

import java.util.List;

public interface IUserService {

    User register(RegistrationRequest registrationRequest);

    User updateUser(Long userId, UserUpdateRequest request);

    //Methode pour trouver l'utilisateur par son identifiant !
    User findById(Long userId);

    void delete(Long userId);

    List<UserDto> getAllUsers();
}
