package com.maltseva.servicestation.project.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.time.LocalDate;
import java.util.*;

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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
    private User employer;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "diagnostic_sheet_diagnostic_result",
            joinColumns = @JoinColumn(name = "diagnostic_sheet_id"), foreignKey = @ForeignKey(name = "FK_DIAGNOSTIC_SHEET_DIAGNOSTIC_RESULT"),
            inverseJoinColumns = @JoinColumn(name = "diagnostic_result_id"), inverseForeignKey = @ForeignKey(name = "FK_DIAGNOSTIC_RESULT_DIAGNOSTIC_SHEET"))
    @ToString.Exclude
    private Set<DiagnosticResult> diagnosticResult = new HashSet<>();

}
