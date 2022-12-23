package com.maltseva.servicestation.project.controller;


import com.maltseva.servicestation.project.dto.ServiceDTO;
import com.maltseva.servicestation.project.model.Service;
import com.maltseva.servicestation.project.service.GenericService;
import com.maltseva.servicestation.project.service.ServiceService;
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
@RequestMapping("/service")
@Tag(name = "Услуги", description = "Контроллер для работы с услугами.")
@SecurityRequirement(name = "Bearer Authentication")
public class ServiceController extends GenericController<Service> {

    private final GenericService<Service, ServiceDTO> serviceService;


    public ServiceController(GenericService<Service, ServiceDTO> serviceService) {
        this.serviceService = serviceService;

    }

    @Override
    @Operation(description = "Получить информацию об одной услуге по ее ID", method = "getOne")
    @RequestMapping(value = "/get",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Service> getOne(@RequestParam(value = "serviceId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(serviceService.getOne(id));
    }

    @Operation(description = "Добавить новую услугу")
    @RequestMapping(value = "/add",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Service> add(@RequestBody ServiceDTO newServiceDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceService.createFromDTO(newServiceDTO));
    }

    @Operation(description = "Изменить информацию об одной услуге по ее ID")
    @RequestMapping(value = "/update",
            method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Service> updateWarehouse(@RequestBody ServiceDTO updatedServiceDTO,
                                                   @RequestParam(value = "serviceId") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceService.updateFromDTO(updatedServiceDTO, id));
    }

    @Operation(description = "Получить информацию обо всех услугах по id СТО")
    @RequestMapping(value = "/listAllServiceByServiceStationId",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Service>> listAllServiceByServiceStationId(
            @RequestParam(value = "serviceStationId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((ServiceService) serviceService).getAllServiceByServiceStationId(id));
    }
}
