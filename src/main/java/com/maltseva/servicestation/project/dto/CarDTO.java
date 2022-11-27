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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarDTO extends CommonDTO{

    private Long id;
    private String model;
    private String registrationNumber;
    private String vin;
    private String ownerCar;
    private Integer mileage;
    private Integer year;
    private Long userId;
}
