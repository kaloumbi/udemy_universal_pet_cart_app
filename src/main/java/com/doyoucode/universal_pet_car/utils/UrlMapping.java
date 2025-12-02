package com.doyoucode.universal_pet_car.utils;

public class UrlMapping {

    /* ========================== FOR API =============================== */
    public static final String API = "/api/v1";

    /* ========================== FOR USERS =============================== */

    public static final String USERS = API+"/users";

    public static final String REGISTER = "/register";
    public static final String UPDATE_USER = "/{userId}/update";
    public static final String GET_USER_BY_ID = "/user/{userId}";
    public static final String DELETE_USER_BY_ID = "/{userId}/delete";
    public static final String GET_ALL_USERS = "/all-users";


    /* ========================== FOR APPOINTMENTS =============================== */

    public static final  String APPOINTMENTS = API + "/appointments";

    public static final String ALL_APPOINTMENTS = "/all";
    public static final String BOOK_APPOINTMENT = "/book-appointment";
    public static final String GET_APPOINTMENT_BY_ID = "/appointment/{id}/appointment";
    public static final String GET_APPOINTMENT_BY_NO = "/appointment/{appointmentNo}/appointmentNo";
    public static final String DELETE_APPOINTMENT = "/appointment/{id}/delete";
    public static final String UPDATE_APPOINTMENT = "/appointment/{id}/update";


    /* ========================== FOR PETS =============================== */
    public static final String PETS = API + "/pets";
    public static final String SAVE_PETS_FOR_APPOINTMENT = "/save-pets";
    public static final String GET_PET_BY_ID = "/pet/{id}/pet";
    public static final String DELETE_PET = "/pet/{id}/deleted";
    public static final String UPDATE_PET = "/pet/{id}/updated";
}
