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
 * The unit class is associated with the spare part by a one-to-many relationship
 *
 * @author Maltseva
 * @version 1.0
 * @since 05.11.2022
 */

@Entity
@Table(name = "units")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "default_gen", sequenceName = "units_seq", allocationSize = 1)
public class Unit extends GenericModel {

    @Serial
    private static final long serialVersionUID = 7958107941861196252L;

    @Column(name = "name", nullable = false)
    String name;
}
