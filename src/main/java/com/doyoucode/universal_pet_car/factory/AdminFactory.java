package com.doyoucode.universal_pet_car.factory;

import com.doyoucode.universal_pet_car.entity.Admin;
import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.repository.AdminRepo;
import com.doyoucode.universal_pet_car.request.RegistrationRequest;
import com.doyoucode.universal_pet_car.service.user.UserAttributesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminFactory {

    private final AdminRepo adminRepo;

    private final UserAttributesMapper userAttributesMapper;

    public Admin createAdmin(RegistrationRequest registrationRequest) {
        Admin admin = new Admin();
        userAttributesMapper.setCommonAttributes(registrationRequest, admin);

        return adminRepo.save(admin);
    }
}
