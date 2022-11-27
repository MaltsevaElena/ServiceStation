package com.maltseva.servicestation.project.dto;

import lombok.*;

import java.util.Set;
/**
 *
 * @author Maltseva
 * @version 1.0
 * @since 27.11.2022
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTO extends CommonDTO {

    private Long id;
    private String name;
    private Long serviceStationID;
    private Set<Long> sparePartSet;
}
