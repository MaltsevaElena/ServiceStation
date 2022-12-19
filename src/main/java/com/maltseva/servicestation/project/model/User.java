package com.maltseva.servicestation.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

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
 * @since 25.11.2022
 */


@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name = "Unique_login_user", columnNames = "login")})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "address")
    private String address;

    @Column(name = "date_birth")
    private LocalDate dateBirth;

    @Column(name = "back_up_email", nullable = false)
    private String backUpEmail;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_USERS_ROLES"), nullable = false)
    @ToString.Exclude
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Role role;


    //--поля для сотрудника--
    //будут null, если user несотрудник СТО

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", foreignKey = @ForeignKey(name = "FK_USERS_POSITIONS"))
    @ToString.Exclude
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_chief_id",
            foreignKey = @ForeignKey(name = "FK_USERS_USERS"))
    @ToString.Exclude
    @JsonIgnore
    private User chief;

}
