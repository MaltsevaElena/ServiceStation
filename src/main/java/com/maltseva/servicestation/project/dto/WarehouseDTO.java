package com.maltseva.servicestation.project.dto;

import lombok.*;

import java.util.Set;
/**
 * @author Maltseva
 * @version 1.0
 * @since 13.12.2022
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTO extends CommonDTO {

    private Long id;
    private String name;
    private Long serviceStationId;

}
