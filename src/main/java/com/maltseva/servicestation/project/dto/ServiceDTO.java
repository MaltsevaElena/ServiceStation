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
@NoArgsConstructor
@AllArgsConstructor

public class ServiceDTO extends CommonDTO {

    private Long id;
    private String name;
    private String code;
    private Double rateHour;
    private Long serviceStationId;

}
