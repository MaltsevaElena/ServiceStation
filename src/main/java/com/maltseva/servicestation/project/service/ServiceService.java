package com.maltseva.servicestation.project.service;

import com.maltseva.servicestation.project.dto.ServiceDTO;
import com.maltseva.servicestation.project.model.Service;
import com.maltseva.servicestation.project.model.ServiceStation;
import com.maltseva.servicestation.project.repository.ServiceRepository;
import com.maltseva.servicestation.project.repository.ServiceStationRepository;
import org.webjars.NotFoundException;

import java.util.List;
/**
 * @author Maltseva
 * @version 1.0
 * @since 13.12.2022
 */
@org.springframework.stereotype.Service
public class ServiceService extends GenericService<Service, ServiceDTO> {

    private final ServiceRepository serviceRepository;
    private final ServiceStationRepository serviceStationRepository;

    public ServiceService(ServiceRepository serviceRepository, ServiceStationRepository serviceStationRepository) {
        this.serviceRepository = serviceRepository;
        this.serviceStationRepository = serviceStationRepository;
    }

    @Override
    public Service updateFromEntity(Service object) {
        return serviceRepository.save(object);
    }

    @Override
    public Service updateFromDTO(ServiceDTO object, Long objectId) {
        Service service = serviceRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Service with such id = " + objectId + " not found"));
        updateFromServiceDTO(object, service);
        return serviceRepository.save(service);
    }

    private void updateFromServiceDTO(ServiceDTO object, Service service) {
        service.setName(object.getName());
        service.setCode(object.getCode());
        service.setRateHour(object.getRateHour());
        ServiceStation serviceStation = serviceStationRepository.findById(object.getServiceStationId()).orElseThrow(
                () -> new NotFoundException("Service station with such id = " + object.getServiceStationId() + " not found"));
        service.setServiceStation(serviceStation);
    }

    @Override
    public Service createFromEntity(Service newObject) {
        return serviceRepository.save(newObject);
    }

    @Override
    public Service createFromDTO(ServiceDTO newObject) {
        Service newService = new Service();
        updateFromServiceDTO(newObject, newService);
        return serviceRepository.save(newService);
    }


    public void delete(Long objectId) {
        Service service = serviceRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Service with such id = " + objectId + " not found"));
        serviceRepository.delete(service);
    }

    @Override
    public Service getOne(Long objectId) {
        return serviceRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Service with such id = " + objectId + " not found"));
    }

    @Override
    public List<Service> listAll() {
        return serviceRepository.allService();
    }


    public List<Service> getAllServiceByServiceStationId(Long serviceStationId) {
        return serviceRepository.findByServiceStationId(serviceStationId);
    }

}
