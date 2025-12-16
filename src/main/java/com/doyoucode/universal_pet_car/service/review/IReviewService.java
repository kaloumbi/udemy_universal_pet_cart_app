package com.doyoucode.universal_pet_car.service.review;

import com.doyoucode.universal_pet_car.entity.Review;
import com.doyoucode.universal_pet_car.request.ReviewUpdateRequest;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public interface IReviewService {

    Review saveReview(Review review, Long reviewId, Long veterinarianId);

    double getAverageRatingForVet(Long reviewId, Long veterinarianId);

    Review updateReview(Long reviewerId, ReviewUpdateRequest review);

    Page<Review> findAllReviewsByUserId(Long userId, int page, int size);

}
