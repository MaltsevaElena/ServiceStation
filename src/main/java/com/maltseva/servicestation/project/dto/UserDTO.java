package com.maltseva.servicestation.project.dto;

import com.maltseva.servicestation.project.model.*;
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

public class UserDTO extends UserAuthorizationDTO{

    private String lastName;
    private String firstName;
    private String middleName;
    private String address;

    private Set<Car> carSet;

}
