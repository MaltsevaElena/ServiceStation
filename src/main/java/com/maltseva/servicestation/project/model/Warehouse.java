package com.maltseva.servicestation.project.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serial;


/**
 * The warehouse class is associated:
 * many-to-one with class service station
 * one-to-many with class spare part
 *
 * @author Maltseva
 * @version 1.0
 * @since 05.11.2022
 */

@Entity
@Table(name = "warehouses")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "default_gen", sequenceName = "warehouses_seq", allocationSize = 1)
public class Warehouse extends GenericModel{

    @Serial
    private static final long serialVersionUID = 4402329028629712473L;

    @Column(name = "name", nullable = false, unique = true)
    String name;

    @Column(name = "service_station_id", insertable = false, updatable = false, nullable = false)
    Integer serviceStationID;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private ServiceStation serviceStation;

}
