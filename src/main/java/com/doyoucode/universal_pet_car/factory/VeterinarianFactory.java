package com.doyoucode.universal_pet_car.factory;

import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.entity.Veterinarian;
import com.doyoucode.universal_pet_car.repository.VeterinarianRepo;
import com.doyoucode.universal_pet_car.request.RegistrationRequest;
import com.doyoucode.universal_pet_car.service.user.UserAttributesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VeterinarianFactory {

    private final VeterinarianRepo veterinarianRepo;

    private final UserAttributesMapper userAttributesMapper;

    public Veterinarian createVeterinarian(RegistrationRequest registrationRequest) {

        Veterinarian veterinarian = new Veterinarian();
        userAttributesMapper.setCommonAttributes(registrationRequest, veterinarian);
        veterinarian.setSpecialization(registrationRequest.getSpecialization());

        return veterinarianRepo.save(veterinarian);
    }
}
