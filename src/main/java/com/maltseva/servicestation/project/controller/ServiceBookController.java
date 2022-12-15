package com.maltseva.servicestation.project.controller;

import com.maltseva.servicestation.project.dto.ServiceBookDTO;
import com.maltseva.servicestation.project.model.ServiceBook;
import com.maltseva.servicestation.project.service.GenericService;
import com.maltseva.servicestation.project.service.ServiceBookService;
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
@RequestMapping("/ServiceBooks")
@Tag(name = "Сервисная книжка", description = "Контроллер для работы с записями в сервисной книжке.")
public class ServiceBookController extends GenericController<ServiceBook> {

    private final GenericService<ServiceBook, ServiceBookDTO> serviceBookService;

    public ServiceBookController(GenericService<ServiceBook, ServiceBookDTO> serviceBookService) {
        this.serviceBookService = serviceBookService;
    }

    @Override
    @Operation(description = "Получить информацию об одной записи из сервисной книжке по ее ID", method = "getOne")
    @RequestMapping(value = "/getServiceBook", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceBook> getOne(@RequestParam(value = "ServiceBookId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(serviceBookService.getOne(id));
    }


    @Operation(description = "Получить информацию обо всех записях в сервисной книжке по ID автомобиля")
    @RequestMapping(value = "/listServiceBookByCar", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ServiceBook>> listAllServiceBookByCar(@RequestParam(value = "CarId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((ServiceBookService) serviceBookService).allServiceBookByCar(id));
    }


    @Operation(description = "Добавить новую запись в сервисную книжку автомобиля")
    @RequestMapping(value = "/addServiceBook", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceBook> add(@RequestBody ServiceBookDTO newServiceBookDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceBookService.createFromDTO(newServiceBookDTO));
    }


    @Operation(description = "Изменить информацию в сервисной книжке по ID")
    @RequestMapping(value = "/updateServiceBook", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceBook> updateCar(@RequestBody ServiceBookDTO updatedServiceBookDTO,
                                                 @RequestParam(value = "ServiceBookId") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceBookService.updateFromDTO(updatedServiceBookDTO, id));
    }
}
