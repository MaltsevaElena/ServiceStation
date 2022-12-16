package com.maltseva.servicestation.project.repository;

import com.maltseva.servicestation.project.model.DiagnosticSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Maltseva
 * @version 1.0
 * @since 15.12.2022
 */
public interface DiagnosticSheetRepository extends JpaRepository<DiagnosticSheet, Long> {

    /*@Query(value = "select dd.id, dd.name, dd.check_box from diagnostic_dates dd " +
            "INNER JOIN diagnostic_sheets ds on ds.id = dd.diagnostic_sheets_id " +
            "WHERE ds.id = ?1",nativeQuery = true)
    List<DiagnosticDate> diagnosticDateFindByDiagnosticSheetId (Long diagnosticSheetId);*/


    @Query(value = "select d.id  from diagnostic_sheets d where d.car_id = ?1", nativeQuery = true)
    List<Long> allDiagnosticSheetByCarId(Long carId);

    @Query(value = "select * from diagnostic_sheets", nativeQuery = true)
    List<DiagnosticSheet> allDiagnosticSheet();
}
