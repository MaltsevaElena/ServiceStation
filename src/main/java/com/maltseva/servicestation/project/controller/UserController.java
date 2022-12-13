package com.maltseva.servicestation.project.controller;

import com.maltseva.servicestation.project.dto.CarDTO;
import com.maltseva.servicestation.project.dto.UserDTO;
import com.maltseva.servicestation.project.dto.UserEmployeeDTO;
import com.maltseva.servicestation.project.dto.UserOwnerCarDTO;
import com.maltseva.servicestation.project.model.Car;
import com.maltseva.servicestation.project.model.User;
import com.maltseva.servicestation.project.service.GenericService;
import com.maltseva.servicestation.project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    private final GenericService<Car, CarDTO> carService;
    private final GenericService<User, UserDTO> userService;

    public UserController(GenericService<Car, CarDTO> carService, GenericService<User, UserDTO> userService) {
        this.carService = carService;
        this.userService = userService;
    }

    @Override
    @Operation(description = "Получить всю информацию об одном пользователе по его ID", method = "getOne")
    @RequestMapping(value = "/getUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> getOne(@RequestParam(value = "id") Long id) {
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

    //TODO сделать рекурсивный запрос в БД для получения списка сотрудников в подчинении у сотрудника

}
