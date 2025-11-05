package com.doyoucode.universal_pet_car.service.user;

import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.exceptions.ResourceNotFoundException;
import com.doyoucode.universal_pet_car.factory.UserFactory;
import com.doyoucode.universal_pet_car.repository.UserRepo;
import com.doyoucode.universal_pet_car.request.RegistrationRequest;
import com.doyoucode.universal_pet_car.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepo userRepo;

    private final UserFactory userFactory;

    @Override
    public User register(RegistrationRequest registrationRequest) {

        return userFactory.createUser(registrationRequest);
    }


    @Override
    public User updateUser(Long userId, UserUpdateRequest request){
        User user = findById(userId);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(user.getGender());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setSpecialization(request.getSpecialization());

        return userRepo.save(user);
    }

    //Methode pour trouver l'utilisateur par son identifiant !
    public User findById(Long userId){
        return userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(" User not found !"));
    }
}
