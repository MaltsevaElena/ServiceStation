package com.maltseva.servicestation.project.controller;

import com.maltseva.servicestation.project.dto.*;
import com.maltseva.servicestation.project.model.DiagnosticSheet;
import com.maltseva.servicestation.project.service.DiagnosticSheetService;
import com.maltseva.servicestation.project.service.GenericService;
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
@RequestMapping("/DiagnosticSheets")
@Tag(name = "Диагностические листы", description = "Контроллер для работы с диагностическим листом.")
public class DiagnosticSheetController extends GenericController<DiagnosticSheet> {

    private final GenericService<DiagnosticSheet, DiagnosticSheetDTO> diagnosticSheetService;

    public DiagnosticSheetController(GenericService<DiagnosticSheet, DiagnosticSheetDTO> diagnosticSheetService) {
        this.diagnosticSheetService = diagnosticSheetService;

    }


    @Override
    @Operation(description = "Получить информацию об одном диагностическом листе по его ID", method = "getOne")
    @RequestMapping(value = "/getDiagnosticSheet", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiagnosticSheet> getOne(@RequestParam(value = "DiagnosticSheetId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(diagnosticSheetService.getOne(id));
    }

    @Operation(description = "Получить ВСЮ информацию об одном диагностическом листе по его ID", method = "getOne")
    @RequestMapping(value = "/getDiagnosticSheetDTO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiagnosticSheetDTO> getDiagnosticSheetDTO(@RequestParam(value = "DiagnosticSheetId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((DiagnosticSheetService) diagnosticSheetService).getOneDiagnosticSheetDTO(id));
    }

    @Operation(description = "Получить id всех листов диагностически по ID автомобиля")
    @RequestMapping(value = "/getAllDiagnosticSheetByCarID", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Long>> listDiagnosticSheetByCar(@RequestParam(value = "car_id") Long carId) {
        return ResponseEntity.status(HttpStatus.OK).body(((DiagnosticSheetService) diagnosticSheetService).diagnosticSheetByCar(carId));
    }

    @Operation(description = "Добавить новый лист диагностики")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiagnosticSheet> add(@RequestBody DiagnosticSheetDTO diagnosticSheet) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(diagnosticSheetService.createFromDTO(diagnosticSheet));
    }

    @Operation(description = "Удалить лист диагноситки по его ID")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@RequestParam(value = "DiagnosticSheetId") Long id) {
        ((DiagnosticSheetService) diagnosticSheetService).delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Пользователь успешно удален");
    }


}
