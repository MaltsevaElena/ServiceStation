package com.maltseva.servicestation.project.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

/**
 * The tariff class is associated with the service by a one-to-many relationship
 *
 * @author Maltseva
 * @version 1.0
 * @since 25.11.2022
 */

@Entity
@Table(name = "tariffs")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "tariff_seq", allocationSize = 1)
public class Tariff extends GenericModel{

    @Serial
    private static final long serialVersionUID = -1795888884515957010L;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "rate_hour", nullable = false)
    private Integer rateHour;

    //При удалении услуги, тариф не удаляется
    @OneToMany(mappedBy = "tariff",cascade = {CascadeType.MERGE,CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private Set<Service> serviceSet = new HashSet<>();

}
