package com.maltseva.servicestation.project.dto;

import com.maltseva.servicestation.project.model.*;
import lombok.*;

import java.time.LocalDate;

/**
 * @author Maltseva
 * @version 1.0
 * @since 13.12.2022
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserDTO extends CommonDTO {

    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String address;
    private LocalDate dateBirth;
    private String phone;
    private RoleDTO role;
    private String backUpEmail;
    private String login;
    private String password;


    public UserDTO(User user) {

        this.id = user.getId();
        this.lastName = user.getLastName();
        this.firstName = user.getFirstName();
        this.middleName = user.getMiddleName();
        this.address = user.getAddress();
        this.dateBirth = user.getDateBirth();
        this.phone = user.getPhone();
        this.role = new RoleDTO(user.getRole());
        this.backUpEmail = user.getBackUpEmail();
        this.login = user.getLogin();
        this.password = user.getPassword();

    }

}
