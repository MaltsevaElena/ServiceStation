package com.maltseva.servicestation.project.controller;

import com.maltseva.servicestation.project.dto.*;
import com.maltseva.servicestation.project.jwtsecurity.JwtTokenUtil;
import com.maltseva.servicestation.project.model.User;
import com.maltseva.servicestation.project.service.GenericService;
import com.maltseva.servicestation.project.service.UserService;
import com.maltseva.servicestation.project.service.userdetails.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * @author Maltseva
 * @version 1.0
 * @since 12.12.2022
 */

@RestController
@RequestMapping("/user")
@Tag(name = "Пользователи", description = "Контроллер для работы с пользователями.")
public class UserController extends GenericController<User> {

    private final CustomUserDetailsService authenticationService;
    private final JwtTokenUtil jwtTokenUtil;


    private final GenericService<User, UserDTO> userService;

    public UserController(CustomUserDetailsService authenticationService,
                          JwtTokenUtil jwtTokenUtil,
                          GenericService<User, UserDTO> userService) {
        this.authenticationService = authenticationService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @RequestMapping(value = "/auth", method = POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> auth(@RequestBody LoginDTO loginDTO) {
        HashMap<String, Object> response = new HashMap<>();
        if (!((UserService) userService).checkPassword(loginDTO)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин/пароль");
        } else {
            UserDetails foundUser = authenticationService.loadUserByUsername(loginDTO.getUsername());
            String token = jwtTokenUtil.generateToken(foundUser);
            response.put("token", token);
            return ResponseEntity.ok().body(response);
        }
    }

    @Operation(description = "Восстановить пароль по Email")
    @RequestMapping(value = "/remember-password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> rememberPassword(@RequestParam(value = "email") String email) {
        // log.info("!!!Changing password!!!!");
        UserDTO userDTO = ((UserService) userService).getUserByEmail(email);
        ((UserService) userService).sendChangePasswordEmail(email, userDTO.getId());
        return ResponseEntity.status(HttpStatus.OK).body("На вашу почту отправлено письмо с восстановление пароля");
    }

    @Operation(description = "Изменить пароль поссле отправки Email")
    @RequestMapping(value = "/change-password/{userId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changePassword(@PathVariable Long userId,
                                                 @RequestParam(value = "Password") String password) {
        ((UserService) userService).changePassword(userId, password);
        return ResponseEntity.status(HttpStatus.OK).body("Пароль успешно изменен");
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
    @RequestMapping(value = "/addUser", method = POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> add(@RequestBody UserDTO newUserDTO) {
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
