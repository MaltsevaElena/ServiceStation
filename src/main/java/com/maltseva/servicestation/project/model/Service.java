package com.maltseva.servicestation.project.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;

/**
 * The service class is associated:
 * many-to-one with the service_station
 *
 * @author Maltseva
 * @version 1.0
 * @since 25.11.2022
 */


@Entity
@Table(name = "services", uniqueConstraints = {@UniqueConstraint(name = "Unique_name_service", columnNames = "name"),
@UniqueConstraint(name = "unique_code_service",columnNames = "code")})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "services_seq", allocationSize = 1)
public class Service extends GenericModel{

    @Serial
    private static final long serialVersionUID = -6723995435023423391L;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "rate_hour", nullable = false)
    private Double rateHour;

    @Column(name = "service_station_id", insertable = false, updatable = false, nullable = false)
    private Long serviceStationID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_station_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_SERVICES_SERVICE_STATIONS"), nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private ServiceStation serviceStation;

}
