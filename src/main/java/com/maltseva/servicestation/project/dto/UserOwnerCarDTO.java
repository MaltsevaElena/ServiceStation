package com.maltseva.servicestation.project.dto;

import com.maltseva.servicestation.project.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class UserOwnerCarDTO extends UserDTO {

    private Set<Long> carSet;

    public UserOwnerCarDTO(User user) {
        super(user);
    }
}
