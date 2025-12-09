package com.doyoucode.universal_pet_car.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String gender;

    @Column(name = "mobile")
    private String phoneNumber;

    private String email;

    private String password;

    private String userType;

    private boolean isEnabled;

    @CreationTimestamp
    private LocalDate createAt;

    @Transient
    private String specialization;

    @Transient
    private List<Appointment> appointments = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Photo photo;

    @Transient
    private List<Review> reviews = new ArrayList<>();










    public  void removeUserPhoto(){
        if(this.getPhoto() != null){
            this.setPhoto(null);
        }
    }
}
