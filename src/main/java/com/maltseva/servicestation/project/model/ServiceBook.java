package com.maltseva.servicestation.project.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.time.LocalDate;


@Entity
@Table(name = "service_books")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "service_books_seq", allocationSize = 1)
public class ServiceBook extends GenericModel {

    @Serial
    private static final long serialVersionUID = -462322220606112748L;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", foreignKey = @ForeignKey(name = "FK_BOOKS_CARS"))
    @ToString.Exclude
    @JsonIgnore
    private Car car;

    @Column(name = "repair_date", nullable = false)
    private LocalDate repairDate;

    @Column(name = "mileage_car", nullable = false)
    private Integer mileageCar;

    @Column(name = "name_spare_part", nullable = false)
    private String nameSparePart;

    @Column(name = "code_spare_part")
    private String codeSparePart;

    @Column(name = "amount_spare_part", nullable = false)
    private Integer amountSparePart;

    @Column(name = "unit_spare_part")
    @Enumerated
    private Unit unitSparePart;

}
