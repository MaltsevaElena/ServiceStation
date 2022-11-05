package com.maltseva.servicestation.project.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serial;

/**
 * The car class is associated with the user by a many-to-one relationship
 *
 * a car cannot exist without a user, so we use the CascadeType.ALL
 *
 * @author Maltseva
 * @version 1.0
 * @since 05.11.2022
 */


@Entity
@Table(name = "cars")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "default_gen", sequenceName = "cars_seq", allocationSize = 1)
public class Car extends GenericModel{

    @Serial
    private static final long serialVersionUID = -4466630071683797900L;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "registration_number", nullable = false)
    private String registrationNumber;

    @Column(name = "vin", nullable = false, unique = true)
    private String vin;

    @Column(name = "owner_car", nullable = false)
    private String ownerCar;

    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Long userId;

    //a car cannot exist without a user, so we use the CascadeType.ALL
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

}
