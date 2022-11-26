package com.maltseva.servicestation.project.dto;


import com.maltseva.servicestation.project.model.Service;
import com.maltseva.servicestation.project.model.Tariff;
import com.maltseva.servicestation.project.model.User;
import com.maltseva.servicestation.project.model.Warehouse;
import lombok.*;

import java.util.HashSet;
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
    private Set<User> employeeSet = new HashSet<>();
    private Set<Warehouse> warehouseSet = new HashSet<>();
    private Set<Service> serviceSet = new HashSet<>();
    private Set<Tariff> tariffSet = new HashSet<>();


}
