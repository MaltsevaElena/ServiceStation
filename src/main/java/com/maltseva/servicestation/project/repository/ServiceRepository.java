package com.maltseva.servicestation.project.repository;

import com.maltseva.servicestation.project.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Maltseva
 * @version 1.0
 * @since 15.12.2022
 */
public interface ServiceRepository extends JpaRepository<Service, Long> {

    @Query(value = "select * from services", nativeQuery = true)
    List<Service> allService();

    List<Service> findByServiceStationId(Long serviceStationId);
}