package com.maltseva.servicestation.project.controller;


import com.maltseva.servicestation.project.dto.SparePartDTO;
import com.maltseva.servicestation.project.dto.WarehouseDTO;
import com.maltseva.servicestation.project.model.SparePart;
import com.maltseva.servicestation.project.model.Warehouse;
import com.maltseva.servicestation.project.service.GenericService;
import com.maltseva.servicestation.project.service.SparePartService;
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
 * @since 12.12.2022
 */

@RestController
@RequestMapping("/SpareParts")
@Tag(name = "Запчасти", description = "Контроллер для работы с запчастями.")
public class SparePartController extends GenericController<SparePart> {

    private final GenericService<SparePart, SparePartDTO> sparePartService;
    private final GenericService<Warehouse, WarehouseDTO> warehouseService;

    public SparePartController(GenericService<SparePart, SparePartDTO> sparePartService,
                               GenericService<Warehouse, WarehouseDTO> warehouseService) {
        this.sparePartService = sparePartService;
        this.warehouseService = warehouseService;
    }

    @Override
    @Operation(description = "Получить информацию об одной запчасти по его ID", method = "getOne")
    @RequestMapping(value = "/getSparePart",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SparePart> getOne(@RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(sparePartService.getOne(id));
    }

    @Operation(description = "Получить информацию обо всех запчастях в наличии по ID склада")
    @RequestMapping(value = "/listSparePartByWarehouseId",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SparePart>> listSparePartByWarehouseId(@RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((SparePartService) sparePartService).listAllSparePartByWarehouseId(id));
    }

    @Operation(description = "Получить информацию обо всех запчастях в наличии")
    @RequestMapping(value = "/listSparePart",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SparePart>> listAllSparePart() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(sparePartService.listAll());
    }

    @Operation(description = "Получить информацию о запчастях по коду на всех складах")
    @RequestMapping(value = "/listSparePartByCode",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SparePart>> listSparePartByCode(@RequestParam(value = "code") String code) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((SparePartService) sparePartService).getSparePartByCode(code));
    }

    @Operation(description = "Получить информацию о запчастях по коду на конкретном складе")
    @RequestMapping(value = "/listSparePartByCodeAndWarehouseId",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SparePart> listSparePartByCodeAndWarehouseId(@RequestParam(value = "code") String code,
                                                                       @RequestParam(value = "warehouseId") Long warehouseId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((SparePartService) sparePartService).getSparePartByCodeAndWarehouseId(code, warehouseId));
    }

    @Operation(description = "Получить информацию о запчастях по имени на всех складах")
    @RequestMapping(value = "/listSparePartByName",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SparePart>> listSparePartByName(@RequestParam(value = "name") String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((SparePartService) sparePartService).getSparePartByName(name));
    }

    @Operation(description = "Получить информацию о запчастях по имени на конкретном складе")
    @RequestMapping(value = "/listSparePartByNameAndWarehouseId",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SparePart>> listSparePartByNameAndWarehouseId(@RequestParam(value = "name") String name,
                                                                             @RequestParam(value = "warehouseId") Long warehouseId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((SparePartService) sparePartService).getSparePartByNameAndWarehouseId(name, warehouseId));
    }

    @Operation(description = "Получить информацию о запчастях по цене на конкретном складе")
    @RequestMapping(value = "/listSparePartByPriceAndWarehouseId",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SparePart>> listSparePartByPriceAndWarehouseId(@RequestParam(value = "price") Double price,
                                                                              @RequestParam(value = "warehouseId") Long warehouseId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((SparePartService) sparePartService).getSparePartByPriceAndWarehouseId(price, warehouseId));
    }

    @Operation(description = "Получить информацию о запчастях по цене ниже или равной указанной на конкретном складе")
    @RequestMapping(value = "/listSparePartByPriceBeforeAndWarehouseId",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SparePart>> listSparePartByPriceBeforeAndWarehouseId(@RequestParam(value = "price") Double price,
                                                                                    @RequestParam(value = "warehouseId") Long warehouseId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((SparePartService) sparePartService).getSparePartByPriceBeforeAndWarehouseId(price, warehouseId));
    }


    @Operation(description = "Добавить новую запчасть")
    @RequestMapping(value = "/addSparePart",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SparePart> add(@RequestBody SparePartDTO newSparePartDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sparePartService.createFromDTO(newSparePartDTO));
    }


    @Operation(description = "Изменить информацию об одной запчасти по его ID")
    @RequestMapping(value = "/updateSparePart",
            method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SparePart> updateSparePart(@RequestBody SparePartDTO updatedSparePartDTO,
                                                     @RequestParam(value = "SparePartId") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sparePartService.updateFromDTO(updatedSparePartDTO, id));
    }

    @Operation(description = "Изменить количесво завчастей по его ID")
    @RequestMapping(value = "/updateAmountSparePart",
            method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SparePart> updateAmountSparePart(@RequestParam(value = "sparePartId") Long sparePartId,
                                                           @RequestParam(value = "amount") Integer amount) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(((SparePartService) sparePartService).setSparePartAmount(sparePartId, amount));
    }


    @Operation(description = "Удалить одну запчасть по ее ID")
    @RequestMapping(value = "/delete",
            method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteSparePart(@RequestParam(value = "sparePartId") Long id) {
        ((SparePartService) sparePartService).delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Запчасть успешно удалена");
    }

}
