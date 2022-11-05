package com.maltseva.servicestation.project.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serial;

/**
 * The service class is associated:
 * many-to-one with the tariff
 * many-to-one with the service_station
 *
 * @author Maltseva
 * @version 1.0
 * @since 05.11.2022
 */


@Entity
@Table(name = "services")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "default_gen", sequenceName = "services_seq", allocationSize = 1)
public class Service extends GenericModel{

    @Serial
    private static final long serialVersionUID = -6723995435023423391L;

    @Column(name = "name", nullable = false, unique = true)
    String name;

    @Column(name = "code", nullable = false, unique = true)
    String code;

    @Column(name = "tariff_id", insertable = false, updatable = false, nullable = false)
    Integer tariffID;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Tariff tariff;

    @Column(name = "service_station_id", insertable = false, updatable = false, nullable = false)
    Integer serviceStationID;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private ServiceStation serviceStation;


}
