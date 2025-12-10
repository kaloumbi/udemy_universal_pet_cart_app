package com.doyoucode.universal_pet_car.repository;

import com.doyoucode.universal_pet_car.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepo extends JpaRepository<Review, Long> {

    @Query(value = "SELECT r FROM Review r WHERE r.patient.id =: userId OR r.veterinarian.id =:userId", nativeQuery = true)
    Page<Review> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

    List<Review> findByVeterinarianId(Long veterinarianId);

    Optional<Review> findByVeterinarianIdAndPatientId(Long veterinarianId, Long reviewId);

}
