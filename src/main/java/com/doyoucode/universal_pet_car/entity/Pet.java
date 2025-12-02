package com.doyoucode.universal_pet_car.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"appointment"}) //ne pas chager un appointment au chargement des pets
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    private String breed;

    private int age;

    private String color;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
}
