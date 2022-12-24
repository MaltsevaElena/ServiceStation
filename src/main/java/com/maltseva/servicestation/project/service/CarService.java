package com.maltseva.servicestation.project.service;

import com.maltseva.servicestation.project.dto.CarDTO;
import com.maltseva.servicestation.project.exception.UpdateCarMileageException;
import com.maltseva.servicestation.project.model.Car;
import com.maltseva.servicestation.project.model.User;
import com.maltseva.servicestation.project.repository.CarRepository;
import com.maltseva.servicestation.project.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Maltseva
 * @version 1.0
 * @since 13.12.2022
 */
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

    private void updateFromCarDTO(CarDTO object, Car car) {
        car.setModel(object.getModel());
        car.setOwnerCar(object.getOwnerCar());
        car.setMileage(object.getMileage());
        car.setRegistrationNumber(object.getRegistrationNumber());
        car.setVin(object.getVin());
        car.setYear(object.getYear());

        User user = userRepository.findById(object.getUserId()).orElseThrow(
                () -> new NotFoundException("User with such id = " + object.getUserId() + " not found"));
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
        return carRepository.allCars();
    }

    /**
     * Метод позволяющий изменить пробег автомобиля.
     * Нельзя устаносить пробег ниже текущего
     *
     * @param carId
     * @param mileage
     * @return
     */
    public Car setCarMileage(Long carId, Integer mileage) throws UpdateCarMileageException {
        Car car = carRepository.findById(carId).orElseThrow(
                () -> new NotFoundException("Car with such id = " + carId + " not found"));
        int oldMileage = car.getMileage();
        if (oldMileage <= mileage) {
            car.setMileage(mileage);
        } else
            throw new UpdateCarMileageException("Пробег автомобиля: " + oldMileage + " нельзя именить на меньшее значение!");

        return carRepository.save(car);
    }

    /**
     * Метод позволяющий передать автомобиль другому пользователю (в случае продажи автомомбиля)
     *
     * @param carId
     * @param newUserId
     * @param ownerCar
     * @return Car
     */
    public Car setOwnerCar(Long carId, Long newUserId, String ownerCar) {
        Car car = carRepository.findById(carId).orElseThrow(
                () -> new NotFoundException("Car with such id = " + carId + " not found"));
        User user = userRepository.findById(newUserId).orElseThrow(
                () -> new NotFoundException("User with such id = " + newUserId + " not found"));
        car.setUser(user);
        car.setOwnerCar(ownerCar);

        return carRepository.save(car);
    }

    /**
     * Метод осуществялет поиск автомобиля по VIN
     *
     * @param vin
     * @return Car
     */
    public Car getCarByVIN(String vin) {
        return carRepository.findByVin(vin).orElseThrow(
                () -> new NotFoundException("Car with such VIN = " + vin + " not found"));
    }

    /**
     * Метод осуществялет поиск автомобиля по регистрационному номеру,
     * т.к. может быть такая ситуация, когда у двух автомобилей один и тот же рег.знак
     * поэтому метод созвращает список.
     * Например,
     * автомобиль продали, а номера сдали в ГАИ и их выдали для другого автомобиля.
     * а у нас в базе автомобиль хранится со старыми номирами
     * и загеристрировали новый авто, с этим же гос номером.
     *
     * @param registrationNumber
     * @return List <Car>
     */
    public List<Car> getCarByRegistrationNumber(String registrationNumber) {
        return carRepository.findByRegistrationNumber(registrationNumber);
    }
}
