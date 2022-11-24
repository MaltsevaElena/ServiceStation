package com.maltseva.servicestation.project.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;

/**
 * The car class is associated with the user by a many-to-one relationship
 *
 * @author Maltseva
 * @version 1.0
 * @since 25.11.2022
 */


@Entity
@Table(name = "cars")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "user_id", unique = true, insertable = false, updatable = false)
    private Long userId;

    /*Машина может существовать без пользователя,
    и может быть передана другому пользователю.
    Поэтому каскада нет*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_CARS_USERS"))
    @ToString.Exclude
    @JsonIgnore
    private User user;

}
