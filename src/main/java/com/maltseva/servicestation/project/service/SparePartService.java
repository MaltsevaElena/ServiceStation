package com.maltseva.servicestation.project.service;

import com.maltseva.servicestation.project.dto.SparePartDTO;
import com.maltseva.servicestation.project.model.SparePart;
import com.maltseva.servicestation.project.model.Warehouse;
import com.maltseva.servicestation.project.repository.SparePartRepository;
import com.maltseva.servicestation.project.repository.WarehouseRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SparePartService extends GenericService<SparePart, SparePartDTO> {

    private final SparePartRepository sparePartRepository;
    private final WarehouseRepository warehouseRepository;

    public SparePartService(SparePartRepository sparePartRepository, WarehouseRepository warehouseRepository) {
        this.sparePartRepository = sparePartRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public SparePart updateFromEntity(SparePart object) {
        return sparePartRepository.save(object);
    }

    @Override
    public SparePart updateFromDTO(SparePartDTO object, Long objectId) {
        SparePart sparePart = sparePartRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Spare part with such id = " + objectId + " not found"));
        updateFromSparePartDTO(object, sparePart);
        return sparePartRepository.save(sparePart);
    }

    public void updateFromSparePartDTO(SparePartDTO object, SparePart sparePart) {
        sparePart.setName(object.getName());
        sparePart.setCode(object.getCode());
        sparePart.setPrice(object.getPrice());
        sparePart.setAmount(object.getAmount());
        sparePart.setUnit(object.getUnit());
        sparePart.setWarehouseID(object.getWarehouseId());
        Warehouse warehouse = warehouseRepository.findById(object.getWarehouseId()).orElseThrow(
                () -> new NotFoundException("Warehouse with such id = " + object.getWarehouseId() + " not found"));
        sparePart.setWarehouse(warehouse);
    }

    @Override
    public SparePart createFromEntity(SparePart newObject) {
        return sparePartRepository.save(newObject);
    }

    @Override
    public SparePart createFromDTO(SparePartDTO newObject) {
        SparePart newSparePart = new SparePart();
        updateFromSparePartDTO(newObject, newSparePart);

        newSparePart.setCreatedBy(newSparePart.getCreatedBy());
        newSparePart.setCreatedWhen(LocalDateTime.now());
        return sparePartRepository.save(newSparePart);
    }

    public void delete(Long objectId) {
        SparePart sparePart = sparePartRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Spare part with such id = " + objectId + " not found"));
        sparePartRepository.delete(sparePart);
    }

    @Override
    public SparePart getOne(Long objectId) {
        return sparePartRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Spare part with such id = " + objectId + " not found"));
    }

    @Override
    public List<SparePart> listAll() {
        return sparePartRepository.findAll();
    }
}
