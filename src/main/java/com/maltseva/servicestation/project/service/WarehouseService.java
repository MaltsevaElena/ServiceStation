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
import java.util.List;

/**
 * @author Maltseva
 * @version 1.0
 * @since 13.12.2022
 */

@Service
public class WarehouseService extends GenericService<Warehouse, WarehouseDTO> {

    private final WarehouseRepository warehouseRepository;
    private final ServiceStationRepository serviceStationRepository;

    private final SparePartRepository sparePartRepository;

    public WarehouseService(WarehouseRepository warehouseRepository,
                            ServiceStationRepository serviceStationRepository,
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
        warehouse.setName(object.getName());

        ServiceStation serviceStation = serviceStationRepository.findById(object.getServiceStationId()).orElseThrow(
                () -> new NotFoundException("Service station with such id = " + object.getServiceStationId() + " not found"));
        warehouse.setServiceStation(serviceStation);
        return warehouseRepository.save(warehouse);
    }

    @Override
    public Warehouse createFromEntity(Warehouse newObject) {
        return warehouseRepository.save(newObject);
    }

    @Override
    public Warehouse createFromDTO(WarehouseDTO newObject) {
        Warehouse newWarehouse = new Warehouse();
        newWarehouse.setName(newObject.getName());

        ServiceStation serviceStation = serviceStationRepository.findById(newObject.getServiceStationId()).orElseThrow(
                () -> new NotFoundException("Service station with such id = " + newObject.getServiceStationId() + " not found"));
        newWarehouse.setServiceStation(serviceStation);

        newWarehouse.setCreatedBy(newWarehouse.getCreatedBy());
        newWarehouse.setCreatedWhen(LocalDateTime.now());
        return warehouseRepository.save(newWarehouse);
    }

    public void delete(Long objectId) throws ServiceException {
        Warehouse warehouse = warehouseRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Warehouse station with such id = " + objectId + " not found"));
        List<SparePart> sparePartList = sparePartRepository.allSparePartByWarehouseId(warehouse.getId());
        if (sparePartList.isEmpty()) {
            warehouseRepository.delete(warehouse);
        } else {
            throw new ServiceException("Невозможно удалить склад, т.к. на складе есть детали");
        }
    }

    @Override
    public Warehouse getOne(Long objectId) {
        return warehouseRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Warehouse with such id = " + objectId + " not found"));
    }

    @Override
    public List<Warehouse> listAll() {
        return warehouseRepository.allWarehouse();
    }

    public List<Warehouse> getAllWarehouseByServiceStationId(Long serviceStationId) {
        return warehouseRepository.findByServiceStationId(serviceStationId);
    }
}
