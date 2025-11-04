package com.doyoucode.universal_pet_car.controller;

import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.request.RegistrationRequest;
import com.doyoucode.universal_pet_car.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public User addUser(@RequestBody RegistrationRequest registrationRequest){

        return userService.addUser(registrationRequest);
    }
}
