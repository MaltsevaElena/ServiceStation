package com.maltseva.servicestation.project.service;

import com.maltseva.servicestation.project.dto.CarDTO;
import com.maltseva.servicestation.project.model.Car;
import com.maltseva.servicestation.project.model.User;
import com.maltseva.servicestation.project.repository.CarRepository;
import com.maltseva.servicestation.project.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarService extends GenericService<Car, CarDTO> {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public CarService(CarRepository carRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Car updateFromEntity(Car object) {
        return carRepository.save(object);
    }

    @Override
    public Car updateFromDTO(CarDTO object, Long objectId) {
        Car car = carRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Car with such id = " + objectId + " not found"));
        updateFromCarDTO(object, car);
        return carRepository.save(car);
    }

    public void updateFromCarDTO(CarDTO object, Car car) {
        car.setModel(object.getModel());
        car.setOwnerCar(object.getOwnerCar());
        car.setMileage(object.getMileage());
        car.setRegistrationNumber(object.getRegistrationNumber());
        car.setVin(object.getVin());
        car.setYear(object.getYear());
        User user = userRepository.findById(object.getUserId()).orElseThrow(
                () -> new NotFoundException("User with such id = " + object.getUserId() + " not found"));
        car.setUserId(object.getUserId());
        car.setUser(user);
    }

    @Override
    public Car createFromEntity(Car newObject) {
        return carRepository.save(newObject);
    }

    @Override
    public Car createFromDTO(CarDTO newObject) {
        Car newCar = new Car();
        updateFromCarDTO(newObject, newCar);
        newCar.setCreatedBy(newObject.getCreatedBy());
        newCar.setCreatedWhen(LocalDateTime.now());
        return carRepository.save(newCar);
    }

    @Override
    public Car getOne(Long objectId) {
        return carRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Car with such id = " + objectId + " not found"));
    }

    @Override
    public List<Car> listAll() {
        return carRepository.findAll();
    }
}
