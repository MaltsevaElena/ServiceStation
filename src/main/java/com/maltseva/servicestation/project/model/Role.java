package com.maltseva.servicestation.project.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serial;

/**
 * The role class is associated with the user by a one-to-many relationship
 *
 * @author Maltseva
 * @version 1.0
 * @since 05.11.2022
 */

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "default_gen", sequenceName = "roles_seq", allocationSize = 1)
public class Role extends GenericModel {

    @Serial
    private static final long serialVersionUID = -8753264738784105924L;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "title", nullable = false)
    private String title;

}
