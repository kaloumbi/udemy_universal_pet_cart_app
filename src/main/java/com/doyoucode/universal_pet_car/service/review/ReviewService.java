package com.doyoucode.universal_pet_car.service.review;

import com.doyoucode.universal_pet_car.dto.ReviewDto;
import com.doyoucode.universal_pet_car.entity.Review;
import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.enums.AppointmentStatus;
import com.doyoucode.universal_pet_car.exceptions.ResourceNotFoundException;
import com.doyoucode.universal_pet_car.exceptions.AlreadyExistsException;
import com.doyoucode.universal_pet_car.repository.AppointmentRepo;
import com.doyoucode.universal_pet_car.repository.ReviewRepo;
import com.doyoucode.universal_pet_car.repository.UserRepo;
import com.doyoucode.universal_pet_car.request.ReviewUpdateRequest;
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
    public Review saveReview(Review review, Long reviewId, Long veterinarianId) {

        // 1. Check if the reviewer is the same as the doctor being reviewed
        if (veterinarianId.equals(reviewId)){
            throw new IllegalArgumentException("Veterinarians can not review themselves");
        }

        // 2. Check if the reviewer has previously submitted a review for this doctor
//        Optional<Review> existingReview = reviewRepo.findByVeterinarianIdAndPatientId(veterinarianId, reviewId);
//        if (existingReview.isPresent()){
//            throw new AlreadyExistsException("You have already rated this veterinarian, you may edit your previous review");
//        }

        // 3. Check if the reviewer has gotten a completed appointment with this doctor
//        boolean hadCompletedAppointments = appointmentRepo.existsByVeterinarianIdAndPatientIdAndAppointmentStatus(veterinarianId, reviewId, AppointmentStatus.COMPLETED);
//        if (!hadCompletedAppointments){
//            throw new IllegalStateException("Sorry, only patient with has a completed appointments with this veterinarian can leave a review !");
//        }
        // 4. Get the veterinariant  (Patient) from the database
        User vet = userRepo.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException(FeedBackMessage.VET_OR_PATIENT_NOT_FOUND));
        // 5. Get the  from reviewer the database.
        User patient = userRepo.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException(FeedBackMessage.VET_OR_PATIENT_NOT_FOUND));

        // 6. Set both to the review
        review.setVeterinarian(vet);
        review.setPatient(patient);
        // 7. Save the review.
        return reviewRepo.save(review);

    }

    @Transactional
    @Override
    public double getAverageRatingForVet(Long veterinarianId) {
        List<Review> reviews = reviewRepo.findByVeterinarianId(veterinarianId);

        return reviews.isEmpty() ? 0 : reviews.stream()
                .mapToInt(Review::getStars)
                .average()
                .orElse(0.0);
    }

    @Override
    public Review updateReview(Long reviewerId, ReviewUpdateRequest review) {

        return reviewRepo.findById(reviewerId)
                .map(existingReview -> {
                    existingReview.setStars(review.getStars());
                    existingReview.setFeeback(review.getFeedback());
                    return reviewRepo.save(existingReview);
                }).orElseThrow(() -> new ResourceNotFoundException(FeedBackMessage.RESOURCE_NOT_FOUND));

    }

    @Override
    public Page<Review> findAllReviewsByUserId(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return reviewRepo.findAllByUserId(userId, pageRequest);
    }




    // Steps:

    // Implement the delete method to enable users to edit review their review
    @Override
    public void deleteReview(Long reviewerId){

        // 1. get the review from the database
        // 2. Check and remove all relationships between the review and other users (patient and veterinarian)
        reviewRepo.findById(reviewerId)
                .ifPresentOrElse(Review::removeRelationShip, () -> {
                    throw new ResourceNotFoundException(FeedBackMessage.RESOURCE_NOT_FOUND);
                });
        // 3. Delete the Review
        reviewRepo.deleteById(reviewerId);
    }

}
