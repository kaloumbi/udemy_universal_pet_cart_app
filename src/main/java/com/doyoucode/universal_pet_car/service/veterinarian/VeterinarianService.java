package com.doyoucode.universal_pet_car.service.veterinarian;

import com.doyoucode.universal_pet_car.dto.EntityConverter;
import com.doyoucode.universal_pet_car.dto.UserDto;
import com.doyoucode.universal_pet_car.entity.Appointment;
import com.doyoucode.universal_pet_car.entity.Veterinarian;
import com.doyoucode.universal_pet_car.repository.AppointmentRepo;
import com.doyoucode.universal_pet_car.repository.ReviewRepo;
import com.doyoucode.universal_pet_car.repository.VeterinarianRepo;
import com.doyoucode.universal_pet_car.service.photo.IPhotoService;
import com.doyoucode.universal_pet_car.service.review.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VeterinarianService implements IVeterinarianService {

    private final VeterinarianRepo veterinarianRepo;

    private final EntityConverter<Veterinarian, UserDto> entityConverter;

    private final IReviewService reviewService;

    private final ReviewRepo reviewRepo;

    private final IPhotoService photoService;

    private final AppointmentRepo appointmentRepo;

    @Override
    public List<UserDto> getAllVeteriansWithDetails(){
        List<Veterinarian> veterinarians = veterinarianRepo.findAllByUserType("VET");

        return veterinarians.stream()
                .map(this::mapVeterinarianToUserDto)
                .toList();
    }


    @Override
    public List<UserDto> findAvailableVetsForAppointment(String specialization, LocalDate date, LocalTime time){
        List<Veterinarian> filteredVets = getAvailableVeterinarians(specialization, date, time);
        return filteredVets.stream()
                .map(this::mapVeterinarianToUserDto)
                .toList();
    }

    // Check all veterinarians
    @Override
    public List<Veterinarian> getVeterinariansBySpecialization(String specialization){

        return veterinarianRepo.findBySpecialization(specialization);
    }


    private UserDto mapVeterinarianToUserDto(Veterinarian veterinarian){

        UserDto userDto = entityConverter.mapEntityToDto(veterinarian, UserDto.class);

        Double averageRating = reviewService.getAverageRatingForVet(userDto.getId());

        Long totalReviewer = reviewRepo.countByVeterinarianId(veterinarian.getId());

        userDto.setAverageRating(averageRating);
        userDto.setTotalReviewers(totalReviewer);

        if (veterinarian.getPhoto() != null){
            try {
                byte[] photoBytes = photoService.getImageData(veterinarian.getPhoto().getId());
                userDto.setPhoto(photoBytes);
            }catch (SQLException ex){
                throw new RuntimeException(ex.getMessage());
            }
        }
        return userDto;
    }


    //Check Veterinarians
    private List<Veterinarian> getAvailableVeterinarians(String specialization, LocalDate date, LocalTime time){
        List<Veterinarian> veterinarians = getVeterinariansBySpecialization(specialization);
        return veterinarians.stream()
                .filter(vet -> isVetAvailable(vet, date, time))
                .toList();
    }

    //To check Veterinarian appointment for specific specialization
    private boolean isVetAvailable(Veterinarian veterinarian, LocalDate requestedDate, LocalTime requestedTime){
        if (requestedDate != null && requestedTime != null){
            LocalTime requestedEndTime = requestedTime.plusHours(2);
            return appointmentRepo.findByVeterinarianAndAppointmentDate(veterinarian, requestedDate)
                    .stream()
                    .noneMatch(existingAppointment -> doesAppointmentOverLap(existingAppointment, requestedTime, requestedEndTime));
        }
        return true;
    }


    //Search appointments by time
    private boolean doesAppointmentOverLap(Appointment existingAppointment, LocalTime requestedStartTime, LocalTime requestedEndTime){
        LocalTime existingStartTime = existingAppointment.getAppointmentTime();
        LocalTime existingEndTime = existingStartTime.plusHours(2);
        LocalTime unavailableStartTime = existingStartTime.minusHours(1);
        LocalTime unavailableEndTime = existingEndTime.plusMinutes(170);

        return !requestedStartTime.isAfter(unavailableStartTime) && !requestedEndTime.isAfter(unavailableEndTime);
    }

}
