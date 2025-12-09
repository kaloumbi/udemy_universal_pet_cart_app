package com.doyoucode.universal_pet_car.service.review;

import com.doyoucode.universal_pet_car.entity.Review;
import com.doyoucode.universal_pet_car.exceptions.ResourceNotFoundException;
import com.doyoucode.universal_pet_car.repository.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private final ReviewRepo reviewRepo;


    @Override
    public void saveReview(Review review, Long reviewId, Long veterinarianId) {

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
