package com.doyoucode.universal_pet_car.repository;

import com.doyoucode.universal_pet_car.entity.Appointment;
import com.doyoucode.universal_pet_car.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    Appointment findByAppointmentNo(String appointmentNo);

    boolean existsByVeterinarianIdAndPatientIdAndAppointmentStatus(Long veterinarianId, Long reviewId, AppointmentStatus appointmentStatus);
}
