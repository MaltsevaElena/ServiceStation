package com.maltseva.servicestation.project.repository;

import com.maltseva.servicestation.project.model.ServiceStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Maltseva
 * @version 1.0
 * @since 15.12.2022
 */
public interface ServiceStationRepository extends JpaRepository<ServiceStation, Long> {

    @Query(value = "select * from service_stations", nativeQuery = true)
    List<ServiceStation> allServiceStation();
}