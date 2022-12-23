package com.maltseva.servicestation.project.dto;

import com.maltseva.servicestation.project.model.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Maltseva
 * @version 1.0
 * @since 13.12.2022
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarDTO extends CommonDTO {

    private Long id;
    private String model;
    private String registrationNumber;
    private String vin;
    private String ownerCar;
    private Integer mileage;
    private Integer year;

    private Long userId;


    public CarDTO(Car car) {
        this.id = car.getId();
        this.model = car.getModel();
        this.registrationNumber = car.getRegistrationNumber();
        this.vin = car.getVin();
        this.ownerCar = car.getOwnerCar();
        this.mileage = car.getMileage();
        this.year = car.getYear();
        this.userId = car.getId();

    }
}
