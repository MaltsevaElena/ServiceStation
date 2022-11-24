package com.maltseva.servicestation.project.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;


/**
 * The warehouse class is associated:
 * many-to-one with class service station
 * one-to-many with class spare part
 *
 * @author Maltseva
 * @version 1.0
 * @since 25.11.2022
 */

@Entity
@Table(name = "warehouses")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "warehouses_seq", allocationSize = 1)
public class Warehouse extends GenericModel{

    @Serial
    private static final long serialVersionUID = 4402329028629712473L;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "service_station_id", insertable = false, updatable = false, nullable = false)
    private Long serviceStationID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_station_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_WAREHOUSES_SERVICE_STATIONS"), nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private ServiceStation serviceStation;


    @OneToMany(mappedBy = "warehouse",cascade = {CascadeType.MERGE,CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private Set<SparePart> sparePartSet = new HashSet<>();
}
