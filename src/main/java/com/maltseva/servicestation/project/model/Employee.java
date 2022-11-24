package com.maltseva.servicestation.project.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

/**
 * The employee class is associated:
 *  one-to-one with class user
 *  many-to-one with class position
 *  many-to-one with class serviceStation
 *
 *  @author Maltseva
 *  @version 1.0
 *  @since 25.11.2022
 */

@Entity
@Table(name = "employees")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "employees_seq", allocationSize = 1)
public class Employee extends GenericModel{

    @Serial
    private static final long serialVersionUID = 6892226587859874337L;

    @Column(name = "user_id", insertable = false, updatable = false, nullable = false, unique = true)
    private Long userID;

    //Сотрудник не может существовать без User
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_USER_EMPLOYEE"), nullable = false)
    @ToString.Exclude
    private User user;

    @Column(name = "position_id", insertable = false, updatable = false, nullable = false)
    private Long positionID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_EMPLOYEES_POSITIONS"), nullable = false)
    @ToString.Exclude
    private Position position;


    @Column(name = "service_station_id", insertable = false, updatable = false, nullable = false)
    private Long serviceStationID;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "service_station_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_EMPLOYEES_SERVICE_STATIONS"), nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private ServiceStation serviceStation;

    @Column(name = "employee_chief_id", insertable = false, updatable = false)
    private Long employeeChiefID;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_chief_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_EMPLOYEES_EMPLOYEES"))
    @ToString.Exclude
    @JsonIgnore
    private Employee chief;

    @OneToMany(mappedBy = "chief", cascade = {CascadeType.MERGE, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private Set<Employee> employeeSet = new HashSet<>();


}