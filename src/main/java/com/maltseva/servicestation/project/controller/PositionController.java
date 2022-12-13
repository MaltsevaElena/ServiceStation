package com.maltseva.servicestation.project.controller;

import com.maltseva.servicestation.project.dto.PositionDTO;
import com.maltseva.servicestation.project.model.Position;
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
 * @since 12.12.2022
 */
@RestController
@RequestMapping("/Positions")
@Tag(name = "Должность", description = "Контроллер для работы с должностью сотрудника.")

public class PositionController extends GenericController<Position> {

    private final GenericService<Position, PositionDTO> positionService;

    public PositionController(GenericService<Position, PositionDTO> positionService) {
        this.positionService = positionService;
    }

    @Override
    @Operation(description = "Получить информацию об одной должности по ее ID", method = "getOne")
    @RequestMapping(value = "/getPosition",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Position> getOne(@RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(positionService.getOne(id));
    }

    @Operation(description = "Получить информацию обо всех должностях")
    @RequestMapping(value = "/listPosition",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Position>> listAllPosition() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(positionService.listAll());
    }

    @Operation(description = "Добавить новую должность")
    @RequestMapping(value = "/addPosition",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Position> add(@RequestBody PositionDTO newPositionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(positionService.createFromDTO(newPositionDTO));
    }

    @Operation(description = "Изменить информацию об одной должности по ее ID")
    @RequestMapping(value = "/updatePosition",
            method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Position> updatePosition(@RequestBody PositionDTO updatedPositionDTO,
                                                   @RequestParam(value = "positionId") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(positionService.updateFromDTO(updatedPositionDTO, id));
    }

}
