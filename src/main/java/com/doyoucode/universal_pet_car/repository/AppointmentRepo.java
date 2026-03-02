package com.doyoucode.universal_pet_car.repository;

import com.doyoucode.universal_pet_car.entity.Appointment;
import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.entity.Veterinarian;
import com.doyoucode.universal_pet_car.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    Appointment findByAppointmentNo(String appointmentNo);

    boolean existsByVeterinarianIdAndPatientIdAndAppointmentStatus(Long veterinarianId, Long reviewId, AppointmentStatus appointmentStatus);

    @Query(value = "SELECT a FROM Appointment a WHERE a.patient.id =: userId OR a.veterinarian.id =:userId")
    List<Appointment> findAllByUserId(Long userId);

    List<Appointment> findByVeterinarianAndAppointmentDate(User veterinarian, LocalDate requestedDate);

}
