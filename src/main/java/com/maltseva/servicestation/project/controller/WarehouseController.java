package com.maltseva.servicestation.project.controller;


import com.maltseva.servicestation.project.dto.WarehouseDTO;
import com.maltseva.servicestation.project.model.Warehouse;
import com.maltseva.servicestation.project.service.GenericService;
import com.maltseva.servicestation.project.service.ServiceException;
import com.maltseva.servicestation.project.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Maltseva
 * @version 1.0
 * @since 22.12.2022
 */
@RestController
@RequestMapping("/warehouse")
@Tag(name = "Склад", description = "Контроллер для работы со складом.")
@SecurityRequirement(name = "Bearer Authentication")
public class WarehouseController extends GenericController<Warehouse> {
    private final GenericService<Warehouse, WarehouseDTO> warehouseService;

    public WarehouseController(GenericService<Warehouse, WarehouseDTO> warehouseService) {
        this.warehouseService = warehouseService;
    }

    @Override
    @Operation(description = "Получить информацию об одном складе по его ID", method = "getOne")
    @RequestMapping(value = "/get",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Warehouse> getOne(@RequestParam(value = "warehouseId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(warehouseService.getOne(id));
    }

    @Operation(description = "Добавить новый склад")
    @RequestMapping(value = "/add",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Warehouse> add(@RequestBody WarehouseDTO newWarehouseDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(warehouseService.createFromDTO(newWarehouseDTO));
    }

    @Operation(description = "Изменить информацию об одном складе по его ID")
    @RequestMapping(value = "/update",
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

    @Operation(description = "Получить информацию обо всех складах по id СТО")
    @RequestMapping(value = "/getAllWarehouseByServiceStationId",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Warehouse>> listWarehouseByServiceStationId(@RequestParam(value = "ServiceStationId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((WarehouseService) warehouseService).getAllWarehouseByServiceStationId(id));
    }

}
