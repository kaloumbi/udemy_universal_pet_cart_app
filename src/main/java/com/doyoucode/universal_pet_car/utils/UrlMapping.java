package com.doyoucode.universal_pet_car.utils;

public class UrlMapping {
    public static final String API = "/api/v1";

    public static final String USERS = API+"/users";
    public static final String REGISTER = "/register";
    public static final String UPDATE_USER = "/{userId}/update";
    public static final String GET_USER_BY_ID = "/user/{userId}";
    public static final String DELETE_USER_BY_ID = "/{userId}/delete";
    public static final String GET_ALL_USERS = "/all-users";
}
