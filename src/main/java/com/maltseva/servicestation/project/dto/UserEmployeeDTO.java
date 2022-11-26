package com.maltseva.servicestation.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class UserEmployeeDTO extends UserDTO{

    private Long positionID;
    private Long serviceStationID;
    private Long employeeChiefID;
    private Set<UserDTO> employeeSet;

}
