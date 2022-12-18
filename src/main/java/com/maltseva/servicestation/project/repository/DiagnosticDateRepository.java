package com.maltseva.servicestation.project.repository;

import com.maltseva.servicestation.project.model.DiagnosticDate;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author Maltseva
 * @version 1.0
 * @since 15.12.2022
 */
public interface DiagnosticDateRepository extends JpaRepository<DiagnosticDate, Long> {

    /*@Query(value = "select dd.id, dd.name, dd.check_box from diagnostic_dates dd " +
            "INNER JOIN diagnostic_sheets ds on ds.id = dd.diagnostic_sheets_id " +
            "WHERE ds.id = ?1",nativeQuery = true)
    List<DiagnosticDate> diagnosticDateFindByDiagnosticSheetId (Long diagnosticSheetId);*/
}
