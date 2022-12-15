package com.maltseva.servicestation.project.controller;


import com.maltseva.servicestation.project.dto.ServiceDTO;
import com.maltseva.servicestation.project.dto.ServiceStationDTO;
import com.maltseva.servicestation.project.dto.WarehouseDTO;
import com.maltseva.servicestation.project.model.Service;
import com.maltseva.servicestation.project.model.ServiceStation;
import com.maltseva.servicestation.project.model.Warehouse;
import com.maltseva.servicestation.project.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Maltseva
 * @version 1.0
 * @since 15.12.2022
 */

@RestController
@RequestMapping("/ServiceStations")
@Tag(name = "Станция технического обслуживания", description = "Контроллер для работы с СТО.")
public class ServiceStationController extends GenericController<ServiceStation> {

    private final GenericService<Warehouse, WarehouseDTO> warehouseService;
    private final GenericService<ServiceStation, ServiceStationDTO> serviceStationService;
    private final GenericService<Service, ServiceDTO> serviceService;

    public ServiceStationController(GenericService<Warehouse, WarehouseDTO> warehouseService,
                                    GenericService<ServiceStation, ServiceStationDTO> serviceStationService,
                                    GenericService<Service, ServiceDTO> serviceService) {
        this.warehouseService = warehouseService;
        this.serviceStationService = serviceStationService;
        this.serviceService = serviceService;
    }

    @Override
    @Operation(description = "Получить информацию о СТО по его ID", method = "getOne")
    @RequestMapping(value = "/getServiceStation",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceStation> getOne(@RequestParam(value = "ServiceStationId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(serviceStationService.getOne(id));
    }


    @Operation(description = "Получить информацию обо всех СТО")
    @RequestMapping(value = "/listServiceStations",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ServiceStation>> listAllServiceStations() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(serviceStationService.listAll());
    }

    @Operation(description = "Получить информацию обо всех складах по Id  СТО")
    @RequestMapping(value = "/getWarehousesByServiceStationId",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Warehouse>> listWarehousesByServiceStationId
            (@RequestParam(value = "ServiceStationId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((WarehouseService) warehouseService).getAllWarehouseByServiceStationId(id));
    }

    @Operation(description = "Получить информацию обо всех услугах по Id  СТО")
    @RequestMapping(value = "/getServiceByServiceStationId",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Service>> listServiceByServisStationId
            (@RequestParam(value = "ServiceStationId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((ServiceService) serviceService).getAllServiceByServiceStationId(id));
    }

    @Operation(description = "Добавить новое СТО")
    @RequestMapping(value = "/addServiceStation",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceStation> add(@RequestBody ServiceStationDTO newServiceStationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceStationService.createFromDTO(newServiceStationDTO));
    }

    @Operation(description = "Изменить информацию о СТО по его ID")
    @RequestMapping(value = "/updateServiceStation",
            method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceStation> updateServiceStation(
            @RequestBody ServiceStationDTO updatedServiceStationDTO,
            @RequestParam(value = "ServiceStationId") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceStationService.updateFromDTO(updatedServiceStationDTO, id));
    }

    @Operation(description = "Удалить одно СТО по его ID")
    @RequestMapping(value = "/delete",
            method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteServiceStation(
            @RequestParam(value = "ServiceStationId") Long id) throws ControllerException {
        try {
            ((ServiceStationService) serviceStationService).delete(id);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("СТО успешно удалено");
    }
}