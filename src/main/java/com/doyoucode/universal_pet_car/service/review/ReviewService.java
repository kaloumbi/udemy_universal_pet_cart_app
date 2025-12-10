package com.doyoucode.universal_pet_car.service.review;

import com.doyoucode.universal_pet_car.entity.Appointment;
import com.doyoucode.universal_pet_car.entity.Review;
import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.enums.AppointmentStatus;
import com.doyoucode.universal_pet_car.exceptions.ResourceNotFoundException;
import com.doyoucode.universal_pet_car.exceptions.UserAlreadyExistsException;
import com.doyoucode.universal_pet_car.repository.AppointmentRepo;
import com.doyoucode.universal_pet_car.repository.ReviewRepo;
import com.doyoucode.universal_pet_car.repository.UserRepo;
import com.doyoucode.universal_pet_car.utils.FeedBackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private final ReviewRepo reviewRepo;

    private final AppointmentRepo appointmentRepo;

    private final UserRepo userRepo;


    @Override
    public void saveReview(Review review, Long reviewId, Long veterinarianId) {

        // 1. Check if the reviewer is the same as the doctor being reviewed
        if (veterinarianId.equals(reviewId)){
            throw new IllegalArgumentException("Veterinarians can not review themselves");
        }

        // 2. Check if the reviewer has previously submitted a review for this doctor
        Optional<Review> existingReview = reviewRepo.findByVeterinarianIdAndPatientId(veterinarianId, reviewId);
        if (existingReview.isPresent()){
            throw new UserAlreadyExistsException("You have already rated this veterinarian, you may edit your previous review");
        }

        // 3. Check if the reviewer has gotten a completed appointment with this doctor
        boolean hadCompletedAppointments = appointmentRepo.existsByVeterinarianIdAndPatientIdAndStatus(veterinarianId, reviewId, AppointmentStatus.COMPLETED);
        if (!hadCompletedAppointments){
            throw new IllegalStateException("Sorry, only patient with has a completed appointments with this veterinarian can leave a review !");
        }
        // 4. Get the veterinariant  (Patient) from the database
        User vet = userRepo.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException(FeedBackMessage.RESOURCE_FOUND));
        // 5. Get the  from reviewer the database.
        User user = userRepo.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException(FeedBackMessage.RESOURCE_NOT_FOUND));

        // 6. Set both to the review
        review.setVeterinarian(vet);
        review.setPatient(vet);
        // 7. Save the review.
        reviewRepo.save(review);


    }

    @Transactional
    @Override
    public double getAverageRatingForVet(Long reviewId, Long veterinarianId) {
        List<Review> reviews = reviewRepo.findByVeterinarianId(veterinarianId);

        return reviews.isEmpty() ? 0 : reviews.stream()
                .mapToInt(Review::getStars)
                .average()
                .orElse(0.0);
    }

    @Override
    public void updateReview(Long reviewerId, Review review) {

        reviewRepo.findById(reviewerId).ifPresentOrElse(existingReview -> {
            existingReview.setStars(review.getStars());
            existingReview.setFeeback(review.getFeeback());
            reviewRepo.save(existingReview);
        }, () -> {
            throw new ResourceNotFoundException("Review not found !");
        });
    }

    @Override
    public Page<Review> findAllReviewsByUserId(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return reviewRepo.findAllByUserId(userId, pageRequest);
    }


}
