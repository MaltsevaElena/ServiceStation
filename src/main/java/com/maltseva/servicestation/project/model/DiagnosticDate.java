package com.maltseva.servicestation.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Maltseva
 * @version 1.0
 * @since 17.12.2022
 */


@Entity
@Table(name = "diagnostic_dates")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "diagnostic_dates_seq", allocationSize = 1)
public class DiagnosticDate extends GenericModel {

    @Serial
    private static final long serialVersionUID = 2013471421958589995L;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "diagnosticDatesForDiagnosticSheet", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    private Set<ServiceStation> serviceStation = new HashSet<>();

}
