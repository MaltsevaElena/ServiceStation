package com.maltseva.servicestation.project.controller;

import com.maltseva.servicestation.project.dto.TariffDTO;
import com.maltseva.servicestation.project.model.Tariff;
import com.maltseva.servicestation.project.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


/**
 * @author Maltseva
 * @version 1.0
 * @since 22.12.2022
 */
@RestController
@RequestMapping("/tariff")
@Tag(name = "Тарифы", description = "Контроллер для работы с тарифами.")
@SecurityRequirement(name = "Bearer Authentication")
public class TariffController extends GenericController<Tariff> {

    private final GenericService<Tariff, TariffDTO> tariffService;

    public TariffController(GenericService<Tariff, TariffDTO> tariffService) {
        this.tariffService = tariffService;
    }

    @Override
    @Operation(description = "Получить информацию об одном тарифе по его ID", method = "getOne")
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tariff> getOne(@RequestParam(value = "tariffId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(tariffService.getOne(id));
    }

    @Operation(description = "Получить информацию обо всех тарифах")
    @RequestMapping(value = "/listTariff", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tariff>> listAllTariff() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(tariffService.listAll());
    }

    @Operation(description = "Получить информацию обо всех тарифах по id СТО")
    @RequestMapping(value = "/listTariffByServiceStationId",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tariff>> listTariffByServiceStationId(@RequestParam(value = "ServiceStationId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((TariffService) tariffService).listAllTariffByServiceStationId(id));
    }

    @Operation(description = "Добавить новый тарифф")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tariff> add(@RequestBody TariffDTO newTariffDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tariffService.createFromDTO(newTariffDTO));
    }

    @Operation(description = "Изменить информацию об одном автомобиле по его ID")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tariff> updateTariff(@RequestBody TariffDTO updatedTariffDTO,
                                               @RequestParam(value = "tariffId") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tariffService.updateFromDTO(updatedTariffDTO, id));
    }

    @Operation(description = "Закрыть действие тарифа по его ID")
    @RequestMapping(value = "/updateEndDateTariff", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tariff> updateEndDateTariff(@RequestParam(value = "tariffId") Long id,
                                                      @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(((TariffService) tariffService).setEndDate(id, endDate));
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
