package com.maltseva.servicestation.project.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.time.LocalDate;

/**
 * The car class is associated with the car by a many-to-one relationship
 *
 * @author Maltseva
 * @version 1.0
 * @since 15.12.2022
 */


@Entity
@Table(name = "diagnostic_sheets")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "diagnostic_sheets_seq", allocationSize = 1)
public class DiagnosticSheet extends GenericModel {

    @Serial
    private static final long serialVersionUID = 6324712399077586003L;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", foreignKey = @ForeignKey(name = "FK_DIAGNOSTIC_SHEET_CARS"), nullable = false)
    @ToString.Exclude
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Car car;

    @Column(name = "repair_date", nullable = false)
    private LocalDate repairDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_DIAGNOSTIC_SHEET_EMPLOYER"))
    @ToString.Exclude
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User employer;

    @Column(name = "diagnostic_result")
    private String diagnosticResult;

}
