package com.doyoucode.universal_pet_car.factory;

import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.exceptions.UserAlreadyExistsException;
import com.doyoucode.universal_pet_car.repository.UserRepo;
import com.doyoucode.universal_pet_car.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleUserFactory implements UserFactory {

    private final UserRepo userRepo;

    private final AdminFactory adminFactory;

    private final PatientFactory patientFactory;

    private final VeterinarianFactory veterinarianFactory;

    @Override
    public User createUser(RegistrationRequest registrationRequest) {
        if (userRepo.existsByEmail(registrationRequest.getEmail())){
            throw new UserAlreadyExistsException("Oops! " + registrationRequest.getEmail() + " already exists !");
        }
        switch (registrationRequest.getUserType()){
            case "VET" -> {
                return veterinarianFactory.createVeterinarian(registrationRequest);
            }
            case "PATIENT" ->{
                return patientFactory.createPatient(registrationRequest);
            }
            case "ADMIN" -> {
                return adminFactory.createAdmin(registrationRequest);
            }
            default -> {
                return null;
            }
        }
    }
}
