package com.maltseva.servicestation.project.repository;

import com.maltseva.servicestation.project.model.SparePart;
import com.maltseva.servicestation.project.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Maltseva
 * @version 1.0
 * @since 12.12.2022
 */
public interface SparePartRepository extends JpaRepository<SparePart, Long> {

    @Query(value = "select * from spare_parts WHERE amount > 0", nativeQuery = true)
    List<SparePart> allSparePart();

    @Query(value = "select * from spare_parts where warehouse_id = ?1 and amount > 0", nativeQuery = true)
    List<SparePart> allSparePartByWarehouseId(Long warehouseId);

    @Query(value = "select * from spare_parts  where code = ?1 and warehouse_id = ?2", nativeQuery = true)
    Optional<SparePart> findByCodeAndWarehouseId(String code, Long warehouseId);

    @Query(value = "select * from spare_parts  where code = ?1", nativeQuery = true)
    List<SparePart> findByCode(String code);

    @Query("select s from SparePart s where upper(s.name) like upper(concat('%', ?1, '%')) and s.warehouse.id = ?2")
    List<SparePart> findByNameContainsIgnoreCaseAndWarehouseId(String name, Long warehouseId);

    @Query("select s from SparePart s where upper(s.name) like upper(concat('%', ?1, '%'))")
    List<SparePart> findByNameContainsIgnoreCase(String name);

    @Query(value = "select * from spare_parts where price <= ?1 and warehouse_id = ?2", nativeQuery = true)
    List<SparePart> findByPriceBeforeAndWarehouseId(Double price, Long warehouseId);

    @Query("select s from SparePart s where s.price = ?1 and s.warehouse.id = ?2")
    List<SparePart> findByPriceAndWarehouseId(Double price, Long warehouseId);
}