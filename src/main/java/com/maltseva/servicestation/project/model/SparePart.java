package com.maltseva.servicestation.project.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serial;

/**
 * The spare part class is associated:
 * many-to-one with class unit
 * many-to-one with class warehouse
 *
 *
 * @author Maltseva
 * @version 1.0
 * @since 05.11.2022
 */


@Entity
@Table(name = "spare_parts")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "default_gen", sequenceName = "spare_parts_seq", allocationSize = 1)
public class SparePart extends GenericModel{

    @Serial
    private static final long serialVersionUID = -2881262656264828955L;
    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "code", nullable = false, unique = true)
    String code;

    @Column(name = "price", nullable = false)
    Double price;

    @Column(name = "amount", nullable = false)
    Integer amount;

    @Column(name = "warehouse_id", insertable = false, updatable = false, nullable = false)
    Integer warehouseStationID;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Warehouse warehouse;

    @Column(name = "unit_id", insertable = false, updatable = false, nullable = false)
    Integer unitID;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Unit unit;

}
