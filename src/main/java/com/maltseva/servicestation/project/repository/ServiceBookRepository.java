package com.maltseva.servicestation.project.repository;

import com.maltseva.servicestation.project.model.ServiceBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Maltseva
 * @version 1.0
 * @since 15.12.2022
 */
public interface ServiceBookRepository extends JpaRepository<ServiceBook, Long> {


    @Query(value = "select * from service_books where car_id = ?1 order by repair_date", nativeQuery = true)
    List<ServiceBook> allServiceBookByCarId(Long carId);
}
