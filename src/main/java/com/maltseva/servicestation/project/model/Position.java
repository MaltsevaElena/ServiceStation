package com.maltseva.servicestation.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The position class is associated with the user by a one-to-many relationship
 *
 * @author Maltseva
 * @version 1.0
 * @since 25.11.2022
 */

@Entity
@Table(name = "positions")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "title", nullable = false)
    private String title;

    //Каскада нет, потому что мы не удаляем должность
    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private Set<Employee> employeeSet = new HashSet<>();

}
