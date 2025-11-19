package com.doyoucode.universal_pet_car.utils;

public class UrlMapping {
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

}
