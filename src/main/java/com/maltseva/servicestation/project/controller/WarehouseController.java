package com.maltseva.servicestation.project.controller;

import com.maltseva.servicestation.project.dto.ServiceStationDTO;
import com.maltseva.servicestation.project.dto.WarehouseDTO;
import com.maltseva.servicestation.project.model.ServiceStation;
import com.maltseva.servicestation.project.model.Warehouse;
import com.maltseva.servicestation.project.service.GenericService;
import com.maltseva.servicestation.project.service.ServiceException;
import com.maltseva.servicestation.project.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Maltseva
 * @version 1.0
 * @since 12.12.2022
 */

@RestController
@RequestMapping("/Warehouses")
@Tag(name = "Склад", description = "Контроллер для работы со складом.")
public class WarehouseController extends GenericController<Warehouse> {

    private final GenericService<Warehouse, WarehouseDTO> warehouseService;
    private final GenericService<ServiceStation, ServiceStationDTO> serviceStationService;

    public WarehouseController(GenericService<Warehouse, WarehouseDTO> warehouseService,
                               GenericService<ServiceStation, ServiceStationDTO> serviceStationService) {
        this.warehouseService = warehouseService;
        this.serviceStationService = serviceStationService;
    }

    @Override
    @Operation(description = "Получить информацию об одном складе по его ID", method = "getOne")
    @RequestMapping(value = "/getWarehouse",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Warehouse> getOne(@RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(warehouseService.getOne(id));
    }


    @Operation(description = "Получить информацию обо всех складах")
    @RequestMapping(value = "/listWarehouses",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Warehouse>> listAllWarehouse() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(warehouseService.listAll());
    }

    @Operation(description = "Получить информацию обо всех складах по Id  СТО")
    @RequestMapping(value = "/getWarehousesByServiceStationId",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Warehouse>> listWarehousesByWarehouseId
            (@RequestParam(value = "ServiceStationId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((WarehouseService) warehouseService).getAllWarehouseByServiceStationId(id));
    }

    @Operation(description = "Добавить новый склад")
    @RequestMapping(value = "/addWarehouse",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Warehouse> add(@RequestBody WarehouseDTO newWarehouseDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(warehouseService.createFromDTO(newWarehouseDTO));
    }

    @Operation(description = "Изменить информацию об одном складе по его ID")
    @RequestMapping(value = "/updateWarehouse",
            method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Warehouse> updateWarehouse(@RequestBody WarehouseDTO updatedWarehouseDTO,
                                                     @RequestParam(value = "warehouseId") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(warehouseService.updateFromDTO(updatedWarehouseDTO, id));
    }

    @Operation(description = "Удалить один склад по его ID")
    @RequestMapping(value = "/delete",
            method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteWarehouse(@RequestParam(value = "warehouseId") Long id) throws ControllerException {
        try {
            ((WarehouseService) warehouseService).delete(id);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Склад успешно удален");
    }


}
