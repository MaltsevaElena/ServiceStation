package com.maltseva.servicestation.project.dto;

import lombok.*;

/**
 * @author Maltseva
 * @version 1.0
 * @since 21.12.2022
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserChiefDTO extends CommonDTO {

    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private Long chiefId;
    private String pathChief;
    private Integer grade;

}
