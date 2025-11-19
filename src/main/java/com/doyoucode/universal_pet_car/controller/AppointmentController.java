package com.doyoucode.universal_pet_car.controller;


import com.doyoucode.universal_pet_car.dto.AppointmentDto;
import com.doyoucode.universal_pet_car.dto.EntityConverter;
import com.doyoucode.universal_pet_car.entity.Appointment;
import com.doyoucode.universal_pet_car.exceptions.ResourceNotFoundException;
import com.doyoucode.universal_pet_car.request.AppointementUpdateRequest;
import com.doyoucode.universal_pet_car.response.ApiResponse;
import com.doyoucode.universal_pet_car.service.appointment.IAppointmentService;
import com.doyoucode.universal_pet_car.utils.FeedBackMessage;
import com.doyoucode.universal_pet_car.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(UrlMapping.APPOINTMENTS)
@RequiredArgsConstructor
public class AppointmentController {

    private final IAppointmentService appointmentService;

    private final EntityConverter<Appointment, AppointmentDto> entityConverter;

    @GetMapping(UrlMapping.ALL_APPOINTMENTS)
    public ResponseEntity<ApiResponse> getAllAppointments(){
        try {
            List<Appointment> appointments = appointmentService.getAllAppointments();

            return ResponseEntity.status(FOUND).body(new ApiResponse(FeedBackMessage.FOUND, appointments));
        } catch (Exception ex) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @PostMapping(UrlMapping.BOOK_APPOINTMENT)
    public ResponseEntity<ApiResponse> bookAppointments(
            @RequestBody Appointment appointment,
            @RequestParam Long senderId,
            @RequestParam Long recipientId
    ){
        try {
            Appointment theAppointment = appointmentService.createAppointment(appointment, senderId, recipientId);
            AppointmentDto appointmentDto = entityConverter.mapEntityToDto(theAppointment, AppointmentDto.class);
            return ResponseEntity.status(FOUND).body(new ApiResponse(FeedBackMessage.SUCCESS, appointmentDto));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ex.getMessage(), null));
        }catch (Exception ex){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.GET_APPOINTMENT_BY_ID)
    public ResponseEntity<ApiResponse> getAppointmentById(@PathVariable Long id){

        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            AppointmentDto appointmentDto = entityConverter.mapEntityToDto(appointment, AppointmentDto.class);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.FOUND, appointmentDto));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ex.getMessage(), null));
        }catch (Exception ex){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @DeleteMapping(UrlMapping.DELETE_APPOINTMENT)
    public ResponseEntity<ApiResponse> deleteAppointmentById(@PathVariable Long id){

        try {
            appointmentService.deleteAppointment(id);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.DELETE_SUCCESS, null));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.GET_APPOINTMENT_BY_NO)
    public ResponseEntity<ApiResponse> getAppointmentByNo(@PathVariable String appointmentNo){

        try {
            Appointment appointment = appointmentService.getAppointmentByNo(appointmentNo);
            AppointmentDto appointmentDto = entityConverter.mapEntityToDto(appointment, AppointmentDto.class);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.FOUND, appointmentDto));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ex.getMessage(), null));
        }catch (Exception ex){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @PutMapping(UrlMapping.UPDATE_APPOINTMENT)
    public ResponseEntity<ApiResponse> updateAppointment(
            @PathVariable Long id,
            @RequestBody AppointementUpdateRequest request){

        try {
            Appointment appointment = appointmentService.updateAppointment(id, request);
            AppointmentDto appointmentDto = entityConverter.mapEntityToDto(appointment, AppointmentDto.class);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.UPDATE_SUCCESS, appointmentDto));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(NOT_ACCEPTABLE).body(new ApiResponse(ex.getMessage(), null));
        }catch (Exception ex){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ex.getMessage(), null));
        }
    }


}
