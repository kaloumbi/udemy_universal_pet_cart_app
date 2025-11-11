package com.doyoucode.universal_pet_car.service.appointment;

import com.doyoucode.universal_pet_car.entity.Appointment;
import com.doyoucode.universal_pet_car.request.AppointementUpdateRequest;
import com.doyoucode.universal_pet_car.request.AppointmentRequest;

public interface IAppointmentService {

    Appointment createAppointment(Appointment appointment, Long sender, Long recipient);

    Appointment updateAppointment(Long id, AppointementUpdateRequest request);

    void deleteAppointment(Long id);

    Appointment getAppointmentById(Long id);

    Appointment getAppointmentByNo(String appointmentNo);
}
