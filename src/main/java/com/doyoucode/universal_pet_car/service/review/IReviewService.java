package com.doyoucode.universal_pet_car.service.review;

import com.doyoucode.universal_pet_car.entity.Review;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public interface IReviewService {

    void saveReview(Review review, Long reviewId, Long veterinarianId);

    double getAverageRatingForVet(Long reviewId, Long veterinarianId);

    void updateReview(Long reviewerId, Review review);

    Page<Review> findAllReviewsByUserId(Long userId, int page, int size);

}
