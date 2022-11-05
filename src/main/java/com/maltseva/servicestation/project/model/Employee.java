package com.maltseva.servicestation.project.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serial;

/**
 * The employee class is associated:
 *  one-to-one with class user
 *  many-to-one with class position
 *  many-to-one with class serviceStation
 *
 *  @author Maltseva
 *  @version 1.0
 *  @since 05.11.2022
 */

@Entity
@Table(name = "employees")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "default_gen", sequenceName = "employees_seq", allocationSize = 1)
public class Employee extends GenericModel{

    @Serial
    private static final long serialVersionUID = 6892226587859874337L;

    @Column(name = "user_id", insertable = false, updatable = false, nullable = false, unique = true)
    private Integer userID;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;

    @Column(name = "position_id", insertable = false, updatable = false, nullable = false)
    private Integer positionID;

    @ManyToOne (cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Position position;

    @Column(name = "service_station_id", insertable = false, updatable = false, nullable = false)
    private Integer serviceStationID;

    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private ServiceStation serviceStation;
}