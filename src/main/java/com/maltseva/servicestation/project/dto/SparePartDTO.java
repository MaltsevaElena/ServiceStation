package com.maltseva.servicestation.project.dto;

import com.maltseva.servicestation.project.model.Unit;
import lombok.*;
/**
 *
 * @author Maltseva
 * @version 1.0
 * @since 27.11.2022
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SparePartDTO extends CommonDTO {

    private Long id;
    private String name;
    private String code;
    private Double price;
    private Integer amount;
    private Unit unit;
    private Long warehouseId;


}
