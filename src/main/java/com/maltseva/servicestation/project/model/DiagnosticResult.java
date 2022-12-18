package com.maltseva.servicestation.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "diagnostic_results")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "diagnostic_results_seq", allocationSize = 1)
public class DiagnosticResult extends GenericModel {

    @Serial
    private static final long serialVersionUID = -9077084296893621764L;

    @Column(name = "diagnostic_sheet_id", nullable = false)
    private Long diagnosticSheetId;

    @Column(name = "diagnostic_date_id", nullable = false)
    private Long serviceStationId;

    @Column(name = "result", nullable = false)
    private Boolean result;

    @JsonIgnore
    @ManyToMany(mappedBy = "diagnosticResult", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    private Set<DiagnosticSheet> diagnosticSheetSet = new HashSet<>();
}
