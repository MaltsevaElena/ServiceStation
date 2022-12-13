package com.maltseva.servicestation.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;

/**
 * The spare part class is associated
 * many-to-one with class warehouse
 *
 *
 * @author Maltseva
 * @version 1.0
 * @since 25.11.2022
 */


@Entity
@Table(name = "spare_parts")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "spare_parts_seq", allocationSize = 1)
public class SparePart extends GenericModel{

    @Serial
    private static final long serialVersionUID = -2881262656264828955L;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "unit")
    @Enumerated
    private Unit unit;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id",
            foreignKey = @ForeignKey(name = "FK_WAREHOUSES_SPARE_PARTS"), nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Warehouse warehouse;

}
