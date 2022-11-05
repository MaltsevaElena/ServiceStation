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
 * The position class is associated with the user by a one-to-many relationship
 *
 * @author Maltseva
 * @version 1.0
 * @since 05.11.2022
 */

@Entity
@Table(name = "positions")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "default_gen", sequenceName = "positions_seq", allocationSize = 1)
public class Position extends GenericModel {

    @Serial
    private static final long serialVersionUID = -655041077487360403L;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "title", nullable = false)
    private String title;

}
