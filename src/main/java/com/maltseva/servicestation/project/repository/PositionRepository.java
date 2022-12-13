package com.maltseva.servicestation.project.repository;

import com.maltseva.servicestation.project.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Maltseva
 * @version 1.0
 * @since 27.11.2022
 */
public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query(value = "select * from positions", nativeQuery = true)
    List<Position> allPosition();

    List<Position> findByName(String name);
}