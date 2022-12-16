package com.maltseva.servicestation.project.controller;

import com.maltseva.servicestation.project.dto.*;
import com.maltseva.servicestation.project.model.Car;
import com.maltseva.servicestation.project.model.User;
import com.maltseva.servicestation.project.service.GenericService;
import com.maltseva.servicestation.project.service.UserService;
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
@RequestMapping("/Users")
@Tag(name = "Пользователи", description = "Контроллер для работы с пользователями.")
public class UserController extends GenericController<User> {


    private final GenericService<User, UserDTO> userService;

    public UserController(GenericService<User, UserDTO> userService) {
        this.userService = userService;
    }

    @Override
    @Operation(description = "Получить всю информацию об одном пользователе по его ID", method = "getOne")
    @RequestMapping(value = "/getUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getOne(@RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getOne(id));
    }

    @Operation(description = "Получить информацию об одном пользователе (владельце автомобиля) по его ID", method = "getOne")
    @RequestMapping(value = "/getUserOwnerCarDTO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserOwnerCarDTO> getOneUserOwnerCarDTO(@RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((UserService) userService).getOneUserOwnerCarDTO(id));
    }

    @Operation(description = "Получить информацию об одном сотруднике", method = "getOne")
    @RequestMapping(value = "/getUserEmployeeDTO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEmployeeDTO> getOneUserEmployeeDTO(@RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(((UserService) userService).getOneUserEmployeeDTO(id));
    }


    @Operation(description = "Получить информацию обо всех автомобилях пользователя по его ID")
    @RequestMapping(value = "/getCarsUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarDTO>> listCarsUser(@RequestParam(value = "user_id") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(((UserService) userService).getCarsUser(userId));
    }

    @Operation(description = "Удалить одного пользователя по его ID")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUser(@RequestParam(value = "userId") Long id) {
        ((UserService) userService).delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Пользователь успешно удален");
    }

    @Operation(description = "Добавить нового пользователя")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> add(@RequestBody UserAuthorizationDTO newUserDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createFromDTO(newUserDTO));
    }


    @Operation(description = "Изменить информацию об одном пользователе по его ID")
    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateCar(@RequestBody UserDTO updatedUserDTO,
                                          @RequestParam(value = "userId") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.updateFromDTO(updatedUserDTO, id));
    }

    //TODO сделать рекурсивный запрос в БД для получения списка сотрудников в подчинении у сотрудника

}
