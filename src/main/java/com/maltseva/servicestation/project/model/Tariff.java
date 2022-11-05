package com.maltseva.servicestation.project.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serial;

/**
 * The tariff class is associated with the service by a one-to-many relationship
 *
 * @author Maltseva
 * @version 1.0
 * @since 05.11.2022
 */

@Entity
@Table(name = "tariffs")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "default_gen", sequenceName = "tariff_seq", allocationSize = 1)
public class Tariff extends GenericModel{

    @Serial
    private static final long serialVersionUID = -1795888884515957010L;

    @Column(name = "name", nullable = false, unique = true)
    String name;

    @Column(name = "rate_hour", nullable = false)
    Long rateHour;
}
