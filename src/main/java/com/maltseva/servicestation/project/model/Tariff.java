package com.maltseva.servicestation.project.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.time.LocalDate;

/**
 * The tariff class is associated with the service station by a many-yo-one relationship
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

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "service_station_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_TARIFF_SERVICE_STATION"), nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private ServiceStation serviceStation;

}
