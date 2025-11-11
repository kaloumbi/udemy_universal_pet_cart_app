package com.doyoucode.universal_pet_car.repository;

import com.doyoucode.universal_pet_car.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    Appointment findByAppointmentNo(String appointmentNo);
}
