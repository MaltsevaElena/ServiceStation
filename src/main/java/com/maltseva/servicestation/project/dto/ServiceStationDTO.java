package com.maltseva.servicestation.project.dto;

import lombok.*;
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
public class ServiceStationDTO extends CommonDTO {

    private Long id;
    private String name;
    private String address;
    private String phone;

}
