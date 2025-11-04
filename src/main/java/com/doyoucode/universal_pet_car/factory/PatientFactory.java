package com.doyoucode.universal_pet_car.factory;

import com.doyoucode.universal_pet_car.entity.Patient;
import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.repository.PatientRepo;
import com.doyoucode.universal_pet_car.request.RegistrationRequest;
import com.doyoucode.universal_pet_car.service.user.UserAttributesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientFactory {

    private final PatientRepo patientRepo;

    private final UserAttributesMapper userAttributesMapper;

    public Patient createPatient(RegistrationRequest registrationRequest) {

        Patient patient = new Patient();
        userAttributesMapper.setCommonAttributes(registrationRequest, patient);
        return patientRepo.save(patient);
    }
}
