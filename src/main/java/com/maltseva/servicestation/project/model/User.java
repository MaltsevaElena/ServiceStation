package com.maltseva.servicestation.project.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serial;
import java.time.LocalDate;

/**
 * The user class is associated:
 * many-to-one with class role
 * one-to-many with class car
 * one-to-one with class employee
 *
 * Email is stored in the login field
 *
 * @author Maltseva
 * @version 1.0
 * @since 05.11.2022
 */


@Entity
@Table (name = "users")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "default_gen", sequenceName = "users_seq", allocationSize = 1)
public class User extends GenericModel {

    @Serial
    private static final long serialVersionUID = -7450553369414997413L;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "date_birth", nullable = false)
    private LocalDate dateBirth;

    @Column(name = "back_up_email")
    private String backupEmail;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "role_id", insertable = false, updatable = false, nullable = false)
    Integer roleID;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Role role;

}
