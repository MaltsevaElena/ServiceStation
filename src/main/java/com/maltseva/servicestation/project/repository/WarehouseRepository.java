package com.maltseva.servicestation.project.repository;

import com.maltseva.servicestation.project.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Maltseva
 * @version 1.0
 * @since 12.12.2022
 */
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    @Query(value = "select * from warehouses", nativeQuery = true)
    List<Warehouse> allWarehouse();


    List<Warehouse> findByServiceStationId(Long serviceStationId);
}