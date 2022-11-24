package com.maltseva.servicestation.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The role class is associated with the user by a one-to-many relationship
 *
 * @author Maltseva
 * @version 1.0
 * @since 25.11.2022
 */

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;


    @Column(name = "description")
    private String description;

    //Каскада нет, потому что мы не удаляем роль
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private Set<User> userSet = new HashSet<>();

}
