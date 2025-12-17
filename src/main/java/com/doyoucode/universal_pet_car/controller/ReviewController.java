package com.doyoucode.universal_pet_car.controller;

import com.doyoucode.universal_pet_car.dto.ReviewDto;
import com.doyoucode.universal_pet_car.entity.Review;
import com.doyoucode.universal_pet_car.exceptions.AlreadyExistsException;
import com.doyoucode.universal_pet_car.exceptions.ResourceNotFoundException;
import com.doyoucode.universal_pet_car.request.ReviewUpdateRequest;
import com.doyoucode.universal_pet_car.response.ApiResponse;
import com.doyoucode.universal_pet_car.service.review.IReviewService;
import com.doyoucode.universal_pet_car.utils.FeedBackMessage;
import com.doyoucode.universal_pet_car.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(UrlMapping.REVIEWS)
@RequiredArgsConstructor
public class ReviewController {

    private final IReviewService reviewService;

    private final ModelMapper modelMapper;


    @PostMapping(UrlMapping.SUBMIT_REVIEW)
    public ResponseEntity<ApiResponse> saveReview(@RequestBody Review review,
                                                  @RequestParam Long reviewId,
                                                  @RequestParam Long vetId
                                                  ){

        try {
            Review saveReview = reviewService.saveReview(review, reviewId, vetId);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.CREATE_SUCCESS, saveReview.getId()));
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.status(NOT_ACCEPTABLE).body(new ApiResponse(ex.getMessage(), null));
        }catch (AlreadyExistsException ex){
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(ex.getMessage(), null));
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ex.getMessage(), null));
        }

    }

    @GetMapping(UrlMapping.GET_AVERAGE_RATING)
    public ResponseEntity<ApiResponse> getAverageRatingForVet(@PathVariable Long vetId){

        try {
            double averageRating = reviewService.getAverageRatingForVet(vetId);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.RESOURCE_FOUND, averageRating));
        } catch (Exception ex) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.GET_USER_REVIEWS)
    public ResponseEntity<ApiResponse> getReviewByUserId(@PathVariable Long userId,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size
                                                         ){
        try {
            Page<Review> reviewPage = reviewService.findAllReviewsByUserId(userId, page, size);
            Page<ReviewDto> reviewDtos = reviewPage.map((element) -> modelMapper.map(element, ReviewDto.class));//conversion de l'entité à dto d'une autre maniere
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.RESOURCE_FOUND, reviewDtos));
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
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.UPDATE_SUCCESS, updateReview.getId()));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ex.getMessage(), null));
        }

    }


    @DeleteMapping(UrlMapping.DELETE_REVIEW)
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long reviewId){

        try {
            reviewService.deleteReview(reviewId);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.DELETE_SUCCESS, null));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ex.getMessage(), null));
        }
    }

}
