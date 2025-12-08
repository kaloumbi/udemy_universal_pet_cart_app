package com.doyoucode.universal_pet_car.service.pet;

import com.doyoucode.universal_pet_car.entity.Pet;
import com.doyoucode.universal_pet_car.exceptions.ResourceNotFoundException;
import com.doyoucode.universal_pet_car.repository.PetRepo;
import com.doyoucode.universal_pet_car.utils.FeedBackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements IPetService {

    private final PetRepo petRepo;

    @Override
    public List<Pet> savePetForAppointment(List<Pet> pets) {

        return petRepo.saveAll(pets);
    }

    @Override
    public Pet updatePet(Long id, Pet pet) {
        Pet existingPet = getPetById(id);
        existingPet.setName(pet.getName());
        existingPet.setAge(pet.getAge());
        existingPet.setColor(pet.getColor());
        existingPet.setType(pet.getType());
        existingPet.setBreed(pet.getBreed());

        return petRepo.save(existingPet);
    }

    @Override
    public void deletePet(Long id) {

        petRepo.findById(id)
                .ifPresentOrElse(petRepo::delete, ()->{
                    throw new ResourceNotFoundException(FeedBackMessage.RESOURCE_NOT_FOUND);
                });

    }

    @Override
    public Pet getPetById(Long id) {
        return petRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(FeedBackMessage.RESOURCE_NOT_FOUND));
    }


}
