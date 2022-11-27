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
@AllArgsConstructor
@NoArgsConstructor
public class ServiceStationEmployeeWarehouseServiceDTO extends ServiceStationDTO {
    //TODO: нужно ли разделить на отдельные ДТО??
    private Set<Long> employeeSet;
    private Set<Long> warehouseSet;
    private Set<Long> serviceSet;
    private Set<Long> tariffSet;
}
