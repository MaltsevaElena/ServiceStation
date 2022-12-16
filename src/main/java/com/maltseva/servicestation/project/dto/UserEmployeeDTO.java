package com.maltseva.servicestation.project.dto;

import com.maltseva.servicestation.project.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Maltseva
 * @version 1.0
 * @since 13.12.2022
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEmployeeDTO extends UserDTO {

    private Long positionID;
    private Long serviceStationID;
    private Long employeeChiefID;

    public UserEmployeeDTO(User user) {
        super(user);
        this.positionID = user.getPosition().getId();
        this.employeeChiefID = user.getChief().getId();
    }

}
