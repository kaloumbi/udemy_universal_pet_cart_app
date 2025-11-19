package com.doyoucode.universal_pet_car.service.appointment;

import com.doyoucode.universal_pet_car.entity.Appointment;
import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.enums.AppointmentStatus;
import com.doyoucode.universal_pet_car.exceptions.ResourceNotFoundException;
import com.doyoucode.universal_pet_car.repository.AppointmentRepo;
import com.doyoucode.universal_pet_car.repository.UserRepo;
import com.doyoucode.universal_pet_car.request.AppointementUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {

    private final AppointmentRepo appointmentRepo;

    private final UserRepo userRepo;


    @Override
    public List<Appointment> getAllAppointments(){
        return appointmentRepo.findAll();
    }

    @Override
    public Appointment createAppointment(Appointment appointment, Long senderId, Long recipientId) {
        Optional<User> sender = userRepo.findById(senderId);
        Optional<User> recipient = userRepo.findById(recipientId);

        if (sender.isPresent() && recipient.isPresent()) {
            appointment.addPatient(recipient.get());
            appointment.addVeterinarian(recipient.get());
            appointment.setAppointmentNo();
            appointment.setAppointmentStatus(AppointmentStatus.WAITING_FOR_APPROVAL);

            return appointmentRepo.save(appointment);
        }

        throw new ResourceNotFoundException("No such appointment !");
    }

    @Override
    public Appointment updateAppointment(Long id, AppointementUpdateRequest request) {
        Appointment existingAppointment = getAppointmentById(id);
        if (! Objects.equals(existingAppointment.getAppointmentStatus(), AppointmentStatus.WAITING_FOR_APPROVAL)){
            throw new IllegalArgumentException("Sorry. this appointment can no longo be updated");
        }
        existingAppointment.setAppointmentDate(request.getAppointmentDate());
        existingAppointment.setAppointmentTime(request.getAppointmentTime());
        existingAppointment.setReason(request.getReason());
        return appointmentRepo.save(existingAppointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepo.findById(id)
                .ifPresentOrElse(appointmentRepo::delete, ()-> {
                    throw new ResourceNotFoundException("Appointment not found !");
                } );
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment !"));
    }

    @Override
    public Appointment getAppointmentByNo(String appointmentNo) {
        return appointmentRepo.findByAppointmentNo(appointmentNo);
    }
}
