package com.doyoucode.universal_pet_car.entity;

import com.doyoucode.universal_pet_car.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reason;

    private LocalDate date;

    private LocalTime time;

    private String appointmentNo;

    private LocalDate createAt;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender")
    private User patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient")
    private User veterinarian;


    public void addPatient(User sender){
        this.setPatient(sender);

        if (sender.getAppointments() == null){
            sender.setAppointments(new ArrayList<>());
        }
        sender.getAppointments().add(this);
    }

    public void addVeterinarian(User recipient){
        this.setPatient(recipient);

        if (recipient.getAppointments() == null){
            recipient.setAppointments(new ArrayList<>());
        }
        recipient.getAppointments().add(this);
    }

    public void setAppointmentNo(String appointmentNo){
        this.appointmentNo = String.valueOf(new Random().nextLong()).substring(1, 11);
    }

}
