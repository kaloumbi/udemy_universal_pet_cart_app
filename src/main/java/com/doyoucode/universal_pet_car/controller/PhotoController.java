package com.doyoucode.universal_pet_car.controller;

import com.doyoucode.universal_pet_car.entity.Photo;
import com.doyoucode.universal_pet_car.exceptions.ResourceNotFoundException;
import com.doyoucode.universal_pet_car.response.ApiResponse;
import com.doyoucode.universal_pet_car.service.photo.IPhotoService;
import com.doyoucode.universal_pet_car.utils.FeedBackMessage;
import com.doyoucode.universal_pet_car.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(UrlMapping.PHOTOS)
@RequiredArgsConstructor
public class PhotoController {

    private final IPhotoService photoService;


    @PostMapping(UrlMapping.UPLOAD_PHOTO)
    public ResponseEntity<ApiResponse> uploadPhoto(@RequestParam MultipartFile file, @RequestParam Long userId) throws SQLException, IOException {

        try {
            Photo thePhoto = photoService.savePhoto(file, userId);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.CREATE_SUCCESS, thePhoto.getId()));
        } catch (IOException | SQLException ex) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(FeedBackMessage.SERVER_ERROR, null));
        }

    }

    @GetMapping(value = UrlMapping.GET_PHOTO_BY_ID)
    public ResponseEntity<ApiResponse> getPhotoById(@PathVariable Long photoId) {
        try {
            Photo photo = photoService.getPhotoById(photoId);
            if (photo != null) {
                byte[] photoBytes = photoService.getImageData(photo.getId());
                return ResponseEntity.ok(new ApiResponse(FeedBackMessage.RESOURCE_FOUND, photoBytes));
            }
        } catch (ResourceNotFoundException | SQLException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(null, NOT_FOUND));
    }



    @PutMapping(UrlMapping.UPDATE_PHOTO)
    public ResponseEntity<ApiResponse> updatePhoto(@PathVariable Long photoId, @RequestBody MultipartFile file) throws SQLException {

        try {
            Photo photo = photoService.getPhotoById(photoId);
            if (photo != null){
                Photo updatePhoto = photoService.updatePhoto(photo.getId(), file);
                return ResponseEntity.ok(new ApiResponse(FeedBackMessage.UPDATE_SUCCESS, updatePhoto.getId()));
            }
        } catch (ResourceNotFoundException | IOException | SQLException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(FeedBackMessage.RESOURCE_NOT_FOUND, null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(FeedBackMessage.SERVER_ERROR, null));

    }


    @DeleteMapping(UrlMapping.DELETE_PHOTO)
    public ResponseEntity<ApiResponse> deletePhoto(@PathVariable Long photoId, @PathVariable Long userId){
        try {
            Photo photo = photoService.getPhotoById(photoId);
            if (photo != null){
                photoService.deletePhoto(photo.getId(), userId);
                return ResponseEntity.ok(new ApiResponse(FeedBackMessage.DELETE_SUCCESS, photo.getId()));
            }

        } catch (ResourceNotFoundException | SQLException ex) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ex.getMessage(), null));
        }

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(null, INTERNAL_SERVER_ERROR));
    }




}
