package com.maltseva.servicestation.project.service;

import com.maltseva.servicestation.project.dto.ServiceStationDTO;
import com.maltseva.servicestation.project.dto.ServiceStationEmployeeWarehouseServiceDTO;
import com.maltseva.servicestation.project.model.*;
import com.maltseva.servicestation.project.repository.*;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ServiceStationService extends GenericService<ServiceStation, ServiceStationDTO> {

    private final ServiceStationRepository serviceStationRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final WarehouseRepository warehouseRepository;
    private final TariffRepository tariffRepository;


    public ServiceStationService(ServiceStationRepository serviceStationRepository, UserRepository userRepository,
                                 ServiceRepository serviceRepository, WarehouseRepository warehouseRepository,
                                 TariffRepository tariffRepository) {
        this.serviceStationRepository = serviceStationRepository;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.warehouseRepository = warehouseRepository;
        this.tariffRepository = tariffRepository;

    }

    @Override
    public ServiceStation updateFromEntity(ServiceStation object) {
        return serviceStationRepository.save(object);
    }

    @Override
    public ServiceStation updateFromDTO(ServiceStationDTO object, Long objectId) {
        ServiceStation serviceStation = serviceStationRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Service station with such id = " + objectId + " not found"));
        updateFromServiceStationDTO(object, serviceStation);
        return serviceStationRepository.save(serviceStation);
    }

    public void updateFromServiceStationDTO(ServiceStationDTO object, ServiceStation serviceStation) {
        serviceStation.setName(object.getName());
        serviceStation.setAddress(object.getAddress());
        serviceStation.setPhone(object.getPhone());

        Set<User> employeeSet = new HashSet<>(userRepository.findAllById(
                ((ServiceStationEmployeeWarehouseServiceDTO) object).getEmployeeSet()));
        serviceStation.setEmployeeSet(employeeSet);

        Set<com.maltseva.servicestation.project.model.Service> serviceSet =
                new HashSet<>(serviceRepository.findAllById(
                        ((ServiceStationEmployeeWarehouseServiceDTO) object).getServiceSet()));
        serviceStation.setServiceSet(serviceSet);

        Set<Warehouse> warehouseSet = new HashSet<>(warehouseRepository.findAllById(
                ((ServiceStationEmployeeWarehouseServiceDTO) object).getWarehouseSet()));
        serviceStation.setWarehouseSet(warehouseSet);

        Set<Tariff> tariffSet = new HashSet<>(tariffRepository.findAllById(
                ((ServiceStationEmployeeWarehouseServiceDTO) object).getTariffSet()));
        serviceStation.setTariffSet(tariffSet);

    }

    @Override
    public ServiceStation createFromEntity(ServiceStation newObject) {
        return serviceStationRepository.save(newObject);
    }

    @Override
    public ServiceStation createFromDTO(ServiceStationDTO newObject) {
        ServiceStation newServiceStation = new ServiceStation();
        updateFromServiceStationDTO(newObject, newServiceStation);
        newServiceStation.setCreatedBy(newObject.getCreatedBy());
        newServiceStation.setCreatedWhen(LocalDateTime.now());
        return serviceStationRepository.save(newServiceStation);
    }

    public void delete(Long objectId) {
        ServiceStation serviceStation = serviceStationRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Service station with such id = " + objectId + " not found"));
        serviceStationRepository.delete(serviceStation);
    }

    @Override
    public ServiceStation getOne(Long objectId) {
        return serviceStationRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Service station with such id = " + objectId + " not found"));
    }

    @Override
    public List<ServiceStation> listAll() {
        return serviceStationRepository.findAll();
    }
}
