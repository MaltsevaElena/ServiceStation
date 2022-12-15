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


/**
 * @author Maltseva
 * @version 1.0
 * @since 12.12.2022
 */
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

    private void updateFromSparePartDTO(SparePartDTO object, SparePart sparePart) {
        sparePart.setName(object.getName());
        sparePart.setCode(object.getCode());
        sparePart.setPrice(object.getPrice());
        sparePart.setAmount(object.getAmount());
        sparePart.setUnit(object.getUnit());
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

    //TODO: можно удалить запчасть только со склада если запчасть ранее использовалась
    // для замены в автомобиле, но больше не закупается иои не продается.
    // Или удалить совсем, если не было использована для автомобилей
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

    /**
     * Возвращает все детали у которых количество больше 0
     * @return List<SparePart>
     */
    @Override
    public List<SparePart> listAll() {
        return sparePartRepository.allSparePart();
    }

    /**
     * Метод возвращает список всех деталей в наличии на конкретном складе
     *
     * @param warehouseId
     * @return List<SparePart>
     */
    public List<SparePart> listAllSparePartByWarehouseId(Long warehouseId) {
        return sparePartRepository.allSparePartByWarehouseId(warehouseId);
    }

    /**
     * Метод изменяет количесво определенной детали на конкретном складе
     *
     * @param sparePartId
     * @param amount
     * @return SparePart
     */
    public SparePart setSparePartAmount(Long sparePartId, Integer amount) {
        SparePart sparePart = sparePartRepository.findById(sparePartId).orElseThrow(
                () -> new NotFoundException("Spare part with such id = " + sparePartId + " not found"));
        sparePart.setAmount(amount);
        return sparePartRepository.save(sparePart);
    }

    /**
     * Поиск детали по коду на конкретном складе
     *
     * @param code
     * @param warehouseId
     * @return SparePart
     */
    public SparePart getSparePartByCodeAndWarehouseId(String code, Long warehouseId) {
        return sparePartRepository.findByCodeAndWarehouseId(code, warehouseId).orElseThrow(
                () -> new NotFoundException("Spare part with such code = " + code + " not found" +
                        " on warehouse id = " + warehouseId));
    }

    /**
     * Поиск детали по коду на всех складах
     *
     * @param code
     * @return List<SparePart>
     */
    public List<SparePart> getSparePartByCode(String code) {
        return sparePartRepository.findByCode(code);
    }

    /**
     * Поиск деталей по имени на конкретном складе
     *
     * @param name
     * @param warehouseId
     * @return List <SparePart>
     */
    public List<SparePart> getSparePartByNameAndWarehouseId(String name, Long warehouseId) {
        return sparePartRepository.findByNameContainsIgnoreCaseAndWarehouseId(name, warehouseId);
    }

    /**
     * Поиск деталей по имени на всех складах
     *
     * @param name
     * @return List <SparePart>
     */
    public List<SparePart> getSparePartByName(String name) {
        return sparePartRepository.findByNameContainsIgnoreCase(name);
    }

    /**
     * Поиск деталей по цене на конкретном складе
     *
     * @param price
     * @param warehouseId
     * @return List<SparePart>
     */
    public List<SparePart> getSparePartByPriceAndWarehouseId(Double price, Long warehouseId) {
        return sparePartRepository.findByPriceAndWarehouseId(price, warehouseId);
    }


    /**
     * Поиск деталей по цене меньше или равной указанному значению
     * на конкретном складе
     *
     * @param price
     * @param warehouseId
     * @return List<SparePart>
     */
    public List<SparePart> getSparePartByPriceBeforeAndWarehouseId(Double price, Long warehouseId) {
        return sparePartRepository.findByPriceBeforeAndWarehouseId(price, warehouseId);
    }

}
