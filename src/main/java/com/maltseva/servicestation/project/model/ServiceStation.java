package com.maltseva.servicestation.project.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;

/**
 * The service station class is associated:
 * one-to-mane with class service
 * one-to-many with class warehouse
 * one-to-many with class employee
 * one-to-many with class tariff
 *
 * @author Maltseva
 * @version 1.0
 * @since 25.11.2022
 */


@Entity
@Table(name = "service_stations")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "service_station_seq", allocationSize = 1)
public class ServiceStation extends GenericModel{

    @Serial
    private static final long serialVersionUID = 2999958129723216329L;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    //let's say there is only one phone
    @Column(name = "phone", nullable = false)
    private String phone;

}
