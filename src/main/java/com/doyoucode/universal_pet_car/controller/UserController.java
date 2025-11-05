package com.doyoucode.universal_pet_car.controller;

import com.doyoucode.universal_pet_car.dto.EntityConverter;
import com.doyoucode.universal_pet_car.dto.UserDto;
import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.exceptions.UserAlreadyExistsException;
import com.doyoucode.universal_pet_car.repository.UserRepo;
import com.doyoucode.universal_pet_car.request.RegistrationRequest;
import com.doyoucode.universal_pet_car.request.UserUpdateRequest;
import com.doyoucode.universal_pet_car.response.ApiResponse;
import com.doyoucode.universal_pet_car.service.user.IUserService;
import com.doyoucode.universal_pet_car.utils.FeedBackMessage;
import com.doyoucode.universal_pet_car.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.ok;

@RequestMapping(UrlMapping.USERS)
@RestController
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    private final UserRepo userRepo;

    private final EntityConverter<User, UserDto> entityConverter;


    @PostMapping(UrlMapping.REGISTER)
    public ResponseEntity<ApiResponse> addUser(@RequestBody RegistrationRequest registrationRequest){

        try {
            User user = userService.register(registrationRequest);
            UserDto userDto = entityConverter.mapEntityToDto(user, UserDto.class);
            return ok(new ApiResponse(FeedBackMessage.SUCCESS, userDto)); //usage du message
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(ex.getMessage(), null));
        }catch (RuntimeException ex){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @PutMapping(UrlMapping.UPDATE_USER)
    public ResponseEntity<ApiResponse> update(@PathVariable Long userId, @RequestBody UserUpdateRequest request){

        try {
            User theUser = userService.updateUser(userId, request);
            UserDto userDto = entityConverter.mapEntityToDto(theUser, UserDto.class);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.UPDATE_SUCCESS, userDto));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ex.getMessage(), null));
        }catch (Exception ex){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ex.getMessage(), null));
        }
    }


}
