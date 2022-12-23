package com.maltseva.servicestation.project.service;

import com.maltseva.servicestation.project.dto.ServiceBookDTO;
import com.maltseva.servicestation.project.model.Car;
import com.maltseva.servicestation.project.model.ServiceBook;
import com.maltseva.servicestation.project.repository.CarRepository;
import com.maltseva.servicestation.project.repository.ServiceBookRepository;
import com.maltseva.servicestation.project.repository.ServiceStationRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;


/**
 * @author Maltseva
 * @version 1.0
 * @since 15.12.2022
 */
@Service
public class ServiceBookService extends GenericService<ServiceBook, ServiceBookDTO> {

    private final ServiceBookRepository serviceBookRepository;
    private final CarRepository carRepository;
    private final ServiceStationRepository serviceStationRepository;

    public ServiceBookService(ServiceBookRepository serviceBookRepository,
                              CarRepository carRepository,
                              ServiceStationRepository serviceStationRepository) {
        this.serviceBookRepository = serviceBookRepository;
        this.carRepository = carRepository;
        this.serviceStationRepository = serviceStationRepository;
    }


    @Override
    public ServiceBook updateFromEntity(ServiceBook object) {
        return serviceBookRepository.save(object);
    }

    @Override
    public ServiceBook updateFromDTO(ServiceBookDTO object, Long objectId) {
        ServiceBook serviceBook = serviceBookRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("ServiceBook with such id = " + objectId + " not found"));

        updateFromServiceBookDTO(object, serviceBook);

        return serviceBookRepository.save(serviceBook);
    }

    private void updateFromServiceBookDTO(ServiceBookDTO objectDTO, ServiceBook serviceBook) {
        serviceBook.setRepairDate(objectDTO.getRepairDate());

        Car car = carRepository.findById(objectDTO.getCarId()).orElseThrow(
                () -> new NotFoundException("Car with such id = " + objectDTO.getCarId() + " not found"));

        Integer mileageCar = car.getMileage();
        if (objectDTO.getMileageCar() > mileageCar) {
            car.setMileage(objectDTO.getMileageCar());
        }
        serviceBook.setMileageCar(objectDTO.getMileageCar());

        serviceBook.setNameSparePart(objectDTO.getNameSparePart());
        serviceBook.setCodeSparePart(objectDTO.getCodeSparePart());
        serviceBook.setAmountSparePart(objectDTO.getAmountSparePart());
        serviceBook.setUnitSparePart(objectDTO.getUnitSparePart());
    }

    @Override
    public ServiceBook createFromEntity(ServiceBook newObject) {
        return serviceBookRepository.save(newObject);
    }

    @Override
    public ServiceBook createFromDTO(ServiceBookDTO newObject) {
        ServiceBook newServiceBook = new ServiceBook();

        Car car = carRepository.findById(newObject.getCarId()).orElseThrow(
                () -> new NotFoundException("Car with such id = " + newObject.getCarId() + " not found"));
        newServiceBook.setCar(car);

        updateFromServiceBookDTO(newObject, newServiceBook);

        newServiceBook.setCreatedBy(newObject.getCreatedBy());
        newServiceBook.setCreatedWhen(newObject.getCreatedWhen());

        return serviceBookRepository.save(newServiceBook);
    }

    @Override
    public ServiceBook getOne(Long objectId) {
        return serviceBookRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("ServiceBook with such id = " + objectId + " not found"));
    }

    @Override
    public List<ServiceBook> listAll() {
        return serviceBookRepository.findAll();
    }

    public List<ServiceBook> allServiceBookByCar(Long carId) {
        return serviceBookRepository.allServiceBookByCarId(carId);
    }

}
