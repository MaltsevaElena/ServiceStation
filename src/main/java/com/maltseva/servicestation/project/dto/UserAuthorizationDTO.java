package com.maltseva.servicestation.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

public class UserAuthorizationDTO extends CommonDTO {

    private Long id;
    private String backupEmail;
    private String login;
    private String password;
    private String phone;

}
