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
@Table(name = "spare_parts", uniqueConstraints = {@UniqueConstraint(name = "Unique_code_spare_part", columnNames = "code")})
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

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "unit")
    @Enumerated
    private Unit unit;

    @Column(name = "warehouse_id", insertable = false, updatable = false, nullable = false)
    private Long warehouseID;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_WAREHOUSES_SPARE_PARTS"), nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Warehouse warehouse;

}
