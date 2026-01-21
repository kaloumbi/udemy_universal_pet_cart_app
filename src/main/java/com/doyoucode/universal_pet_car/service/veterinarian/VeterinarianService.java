package com.doyoucode.universal_pet_car.service.veterinarian;

import com.doyoucode.universal_pet_car.dto.EntityConverter;
import com.doyoucode.universal_pet_car.dto.UserDto;
import com.doyoucode.universal_pet_car.entity.Veterinarian;
import com.doyoucode.universal_pet_car.repository.ReviewRepo;
import com.doyoucode.universal_pet_car.repository.VeterinarianRepo;
import com.doyoucode.universal_pet_car.service.photo.IPhotoService;
import com.doyoucode.universal_pet_car.service.review.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VeterinarianService implements IVeterinarianService {

    private final VeterinarianRepo veterinarianRepo;

    private final EntityConverter<Veterinarian, UserDto> entityConverter;

    private final IReviewService reviewService;

    private final ReviewRepo reviewRepo;

    private final IPhotoService photoService;

    @Override
    public List<UserDto> getAllVeteriansWithDetails(){
        List<Veterinarian> veterinarians = veterinarianRepo.findAllByUserType("VET");

        return veterinarians.stream()
                .map(this::mapVeterinarianToUserDto)
                .toList();
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


}
