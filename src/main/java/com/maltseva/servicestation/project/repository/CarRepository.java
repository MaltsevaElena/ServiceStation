package com.maltseva.servicestation.project.repository;

import com.maltseva.servicestation.project.model.Car;
import com.maltseva.servicestation.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Maltseva
 * @version 1.0
 * @since 27.11.2022
 */
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("select c from Car c where c.user.id = ?1")
    List<Car> findByUserId(Long userId);

    @Query(value = "select * from cars", nativeQuery = true)
    List<Car> allCars();

    @Query(value = "select id from cars where user_id = ?1", nativeQuery = true)
    List<Long> carIdByUserId(Long userId);

    @Query("select c from Car c where c.vin = ?1")
    Optional<Car> findByVin(String vin);

    @Query("select c from Car c where c.registrationNumber = ?1")
    List<Car> findByRegistrationNumber(String registrationNumber);

   
}