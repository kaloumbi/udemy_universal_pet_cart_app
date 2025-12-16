package com.doyoucode.universal_pet_car.controller;

import com.doyoucode.universal_pet_car.entity.Review;
import com.doyoucode.universal_pet_car.exceptions.AlreadyExistsException;
import com.doyoucode.universal_pet_car.exceptions.ResourceNotFoundException;
import com.doyoucode.universal_pet_car.request.ReviewUpdateRequest;
import com.doyoucode.universal_pet_car.response.ApiResponse;
import com.doyoucode.universal_pet_car.service.review.IReviewService;
import com.doyoucode.universal_pet_car.utils.FeedBackMessage;
import com.doyoucode.universal_pet_car.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(UrlMapping.REVIEWS)
@RequiredArgsConstructor
public class ReviewController {

    private final IReviewService reviewService;


    @PostMapping(UrlMapping.SUBMIT_REVIEW)
    public ResponseEntity<ApiResponse> saveReview(@RequestBody Review review,
                                                  @RequestParam Long reviewId,
                                                  @RequestParam Long vetId
                                                  ){

        try {
            Review saveReview = reviewService.saveReview(review, reviewId, vetId);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.CREATE_SUCCESS, saveReview));
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.status(NOT_ACCEPTABLE).body(new ApiResponse(ex.getMessage(), null));
        }catch (AlreadyExistsException ex){
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(ex.getMessage(), null));
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ex.getMessage(), null));
        }

    }

    @GetMapping(UrlMapping.GET_USER_REVIEWS)
    public ResponseEntity<ApiResponse> getReviewByUserId(@PathVariable Long userId,
                                                         @RequestParam int page,
                                                         @RequestParam int size
                                                         ){
        try {
            Page<Review> reviewPage = reviewService.findAllReviewsByUserId(userId, page, size);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.RESOURCE_FOUND, reviewPage));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    @PutMapping(UrlMapping.UPDATE_REVIEW)
    public ResponseEntity<ApiResponse> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateRequest updateRequest ){

        try {
            Review updateReview = reviewService.updateReview(reviewId, updateRequest);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.UPDATE_SUCCESS, updateReview));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ex.getMessage(), null));
        }

    }


    // Implement the delete method to enable users to edit review their review

    // Steps:
    // 1. get the review from the database
    // 2. Check and remove all relationships between the review and other users (patient and veterinarian)
    // 3. Delete the Review


}
