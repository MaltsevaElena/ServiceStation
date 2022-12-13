package com.maltseva.servicestation.project.repository;

import com.maltseva.servicestation.project.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * @author Maltseva
 * @version 1.0
 * @since 12.12.2022
 */
public interface TariffRepository extends JpaRepository<Tariff, Long> {

    @Query(value = "select * from tariffs", nativeQuery = true)
    List<Tariff> allTariff();

    @Query(value = "select * from tariffs  where service_station_id = ?1", nativeQuery = true)
    List<Tariff> allTariffByServiceStationId(Long serviceStationId);
}