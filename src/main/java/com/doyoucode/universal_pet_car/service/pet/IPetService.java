package com.doyoucode.universal_pet_car.service.pet;

import com.doyoucode.universal_pet_car.entity.Pet;

import java.util.List;

public interface IPetService {


    List<Pet> savePetForAppointment(List<Pet> pets);

    Pet updatePet(Long id, Pet pet);

    void deletePet(Long id);

    Pet getPetById(Long id);

}
