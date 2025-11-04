package com.doyoucode.universal_pet_car.service.user;

import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.factory.UserFactory;
import com.doyoucode.universal_pet_car.repository.UserRepo;
import com.doyoucode.universal_pet_car.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final UserFactory userFactory;

    public User addUser(RegistrationRequest registrationRequest) {

        return userFactory.createUser(registrationRequest);
    }
}
