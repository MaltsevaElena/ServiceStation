package com.maltseva.servicestation.project.controller;

import com.maltseva.servicestation.project.dto.CarDTO;
import com.maltseva.servicestation.project.dto.UserDTO;
import com.maltseva.servicestation.project.model.Car;
import com.maltseva.servicestation.project.model.User;
import com.maltseva.servicestation.project.service.CarService;
import com.maltseva.servicestation.project.service.GenericService;
import com.maltseva.servicestation.project.service.ServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Maltseva
 * @version 1.0
 * @since 23.12.2022
 */

@RestController
@RequestMapping("/car")
@Tag(name = "Автомобили", description = "Контроллер для работы с автомобилями.")
@SecurityRequirement(name = "Bearer Authentication")
public class CarController extends GenericController<Car> {

    private final GenericService<Car, CarDTO> carService;
    private final GenericService<User, UserDTO> userService;

    public CarController(GenericService<Car, CarDTO> carService, GenericService<User, UserDTO> userService) {
        this.carService = carService;
        this.userService = userService;
    }

    @Override
    @Operation(description = "Получить информацию об одном автомобиле по его ID", method = "getOne")
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Car> getOne(@RequestParam(value = "carId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(carService.getOne(id));
    }


    @Operation(description = "Получить информацию обо всех автомобилях")
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Car>> listAllCars() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(carService.listAll());
    }

    @Operation(description = "Получить информацию об одном автомобиле по его VIN")
    @RequestMapping(value = "/getCarByVIN", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Car> getCarByVIN(@RequestParam(value = "vin") String vin) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((CarService) carService).getCarByVIN(vin));
    }

    @Operation(description = "Получить информацию об автомобиле по его регистрационному номеру")
    @RequestMapping(value = "/getCarByRegistrationNumber", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Car>> getCarByRegistrationNumber(@RequestParam(value = "registrationNumber") String registrationNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((CarService) carService).getCarByRegistrationNumber(registrationNumber));
    }

    @Operation(description = "Добавить новый автомобиль")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Car> add(@RequestBody CarDTO newCar) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carService.createFromDTO(newCar));
    }


    @Operation(description = "Изменить информацию об одном автомобиле по его ID")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Car> updateCar(@RequestBody CarDTO updatedCarDTO,
                                         @RequestParam(value = "carId") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carService.updateFromDTO(updatedCarDTO, id));
    }

    @Operation(description = "Изменить пробег автомобиля по его ID")
    @RequestMapping(value = "/updateMileageCar", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Car> updateMileageCar(@RequestParam(value = "carId") Long carId,
                                                @RequestParam(value = "mileage") Integer mileage) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(((CarService) carService).setCarMileage(carId, mileage));
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    @Operation(description = "Изменить собственника автомобиля при его продаже, по ID автомобиля")
    @RequestMapping(value = "/updateOwnerCar", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Car> updateOwnerCar(@RequestParam(value = "carId") Long carId,
                                              @RequestParam(value = "newUserId") Long newUserId,
                                              @RequestParam(value = "ownerCar") String ownerCar) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(((CarService) carService).setOwnerCar(carId, newUserId, ownerCar));
    }


}
