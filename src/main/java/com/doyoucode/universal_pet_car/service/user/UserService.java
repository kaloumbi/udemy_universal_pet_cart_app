package com.doyoucode.universal_pet_car.service.user;

import com.doyoucode.universal_pet_car.dto.AppointmentDto;
import com.doyoucode.universal_pet_car.dto.EntityConverter;
import com.doyoucode.universal_pet_car.dto.ReviewDto;
import com.doyoucode.universal_pet_car.dto.UserDto;
import com.doyoucode.universal_pet_car.entity.Review;
import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.exceptions.ResourceNotFoundException;
import com.doyoucode.universal_pet_car.factory.UserFactory;
import com.doyoucode.universal_pet_car.repository.UserRepo;
import com.doyoucode.universal_pet_car.request.RegistrationRequest;
import com.doyoucode.universal_pet_car.request.UserUpdateRequest;
import com.doyoucode.universal_pet_car.service.appointment.IAppointmentService;
import com.doyoucode.universal_pet_car.service.photo.IPhotoService;
import com.doyoucode.universal_pet_car.service.review.IReviewService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepo userRepo;

    private final UserFactory userFactory;

    private final EntityConverter<User, UserDto> entityConverter;

    private final IAppointmentService appointmentService;

    private final IPhotoService photoService;

    private final IReviewService reviewService;

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
    @Override
    public User findById(Long userId){
        return userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(" User not found !"));
    }

    @Override
    public void delete(Long userId){
        userRepo.findById(userId).ifPresentOrElse(userRepo::delete, () -> {
            throw new ResourceNotFoundException(" User not found !");
        });
    }

    @Override
    public List<UserDto> getAllUsers(){
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(user -> entityConverter.mapEntityToDto(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserWithDetails(Long userId) throws SQLException {

        // 1. Get the User
        User user = findById(userId);
        // 2. convert the user to a userDto
        UserDto userDto = entityConverter.mapEntityToDto(user, UserDto.class);
        // 3. get user appointments (users(patient and vet))
        setUserAppointment(userDto);
        setUserPhoto(userDto, user);
        setUserReviews(userDto, userId);

        //à continuer
        return userDto;
    }

    private void setUserAppointment(UserDto userDto){

        List<AppointmentDto> appointmentDtos = appointmentService.getUserAppointments(userDto.getId());
        userDto.setAppointments(appointmentDtos);
    }

    private void setUserPhoto(UserDto userDto, User user) throws SQLException {

        if (user.getPhoto() != null) {
            userDto.setPhotoId(user.getPhoto().getId());
            userDto.setPhoto(photoService.getImageData(user.getPhoto().getId()));
        }
    }

    @SneakyThrows
    private void setUserReviews(UserDto userDto, Long userId){
        Page<Review> reviewPage = reviewService.findAllReviewsByUserId(userId, 0, Integer.MAX_VALUE);

        List<ReviewDto> reviewDtos = reviewPage.getContent()
                .stream()
                .map(this::mapReviewToDto).toList();
        if (!reviewDtos.isEmpty()){
            double averageRating = reviewService.getAverageRatingForVet(userId);
        }
        userDto.setReviews(reviewDtos);
    }

    //Conversion particuliere
    @SneakyThrows
    private ReviewDto mapReviewToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setStars(review.getStars());
        reviewDto.setFeeback(review.getFeeback());
        mapVeterinarianInfo(reviewDto, review);
        mapPatientInfo(reviewDto, review);

        return reviewDto;
    }

    @SneakyThrows
    private void mapPatientInfo(ReviewDto reviewDto, Review review) {
        if (review.getVeterinarian() != null){
            reviewDto.setPatientId(review.getVeterinarian().getId());
            reviewDto.setPatientName(review.getVeterinarian().getFirstName() + " "+ review.getVeterinarian().getLastName());
            // set the photo
            setReviewerPhoto(reviewDto, review);
        }
    }

    private void mapVeterinarianInfo(ReviewDto reviewDto, Review review) throws SQLException {
        if (review.getVeterinarian() != null){
            reviewDto.setVeterinarianId(review.getPatient().getId());
            reviewDto.setVeterinarianName(review.getPatient().getFirstName() + " "+ review.getPatient().getLastName());
            // set the photo
            setVeterinarianPhoto(reviewDto, review);
        }
    }

    private void setReviewerPhoto(ReviewDto reviewDto, Review review) throws SQLException {

        if (review.getPatient().getPhoto() != null){
            try {
                reviewDto.setPatientImage(photoService.getImageData(review.getPatient().getPhoto().getId()));
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }else {
            reviewDto.setPatientImage(null);
        }
    }

    private void setVeterinarianPhoto(ReviewDto reviewDto, Review review) throws SQLException {

        if (review.getVeterinarian().getPhoto() != null){
            try {
                reviewDto.setVeterinarianImage(photoService.getImageData(review.getVeterinarian().getPhoto().getId()));
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }else {
            reviewDto.setVeterinarianName(null);
        }
    }

}
