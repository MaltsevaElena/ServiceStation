package com.maltseva.servicestation.project.service;


import com.maltseva.servicestation.project.dto.WarehouseDTO;
import com.maltseva.servicestation.project.model.ServiceStation;
import com.maltseva.servicestation.project.model.SparePart;
import com.maltseva.servicestation.project.model.Warehouse;
import com.maltseva.servicestation.project.repository.ServiceStationRepository;
import com.maltseva.servicestation.project.repository.SparePartRepository;
import com.maltseva.servicestation.project.repository.WarehouseRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WarehouseService extends GenericService<Warehouse, WarehouseDTO> {

    private final WarehouseRepository warehouseRepository;
    private final ServiceStationRepository serviceStationRepository;
    private final SparePartRepository sparePartRepository;

    public WarehouseService(WarehouseRepository warehouseRepository, ServiceStationRepository serviceStationRepository,
                            SparePartRepository sparePartRepository) {
        this.warehouseRepository = warehouseRepository;
        this.serviceStationRepository = serviceStationRepository;
        this.sparePartRepository = sparePartRepository;
    }

    @Override
    public Warehouse updateFromEntity(Warehouse object) {
        return warehouseRepository.save(object);
    }

    @Override
    public Warehouse updateFromDTO(WarehouseDTO object, Long objectId) {
        Warehouse warehouse = warehouseRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Warehouse with such id = " + objectId + " not found"));
        updateFromWarehouseDTO(object, warehouse);
        return warehouseRepository.save(warehouse);
    }

    public void updateFromWarehouseDTO(WarehouseDTO object, Warehouse warehouse) {
        warehouse.setName(object.getName());
        warehouse.setServiceStationID(object.getServiceStationID());
        ServiceStation serviceStation = serviceStationRepository.findById(object.getServiceStationID()).orElseThrow(
                () -> new NotFoundException("Service station with such id = " + object.getServiceStationID() + " not found"));
        warehouse.setServiceStation(serviceStation);
        Set<SparePart> sparePartSet = new HashSet<>(sparePartRepository.findAllById(object.getSparePartSet()));
        warehouse.setSparePartSet(sparePartSet);
    }

    @Override
    public Warehouse createFromEntity(Warehouse newObject) {
        return warehouseRepository.save(newObject);
    }

    @Override
    public Warehouse createFromDTO(WarehouseDTO newObject) {
        Warehouse newWarehouse = new Warehouse();
        updateFromWarehouseDTO(newObject, newWarehouse);
        newWarehouse.setCreatedBy(newWarehouse.getCreatedBy());
        newWarehouse.setCreatedWhen(LocalDateTime.now());
        return warehouseRepository.save(newWarehouse);
    }

    public void delete(Long objectId) {
        Warehouse warehouse = warehouseRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Warehouse station with such id = " + objectId + " not found"));
        warehouseRepository.delete(warehouse);
    }

    @Override
    public Warehouse getOne(Long objectId) {
        return warehouseRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Warehouse with such id = " + objectId + " not found"));
    }

    @Override
    public List<Warehouse> listAll() {
        return warehouseRepository.findAll();
    }
}
