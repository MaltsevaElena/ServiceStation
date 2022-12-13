package com.maltseva.servicestation.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;

/**
 * The warehouse class is associated:
 * many-to-one with class service station
 *
 * @author Maltseva
 * @version 1.0
 * @since 13.12.2022
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

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_station_id",
            foreignKey = @ForeignKey(name = "FK_WAREHOUSES_SERVICE_STATIONS"), nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private ServiceStation serviceStation;

}
