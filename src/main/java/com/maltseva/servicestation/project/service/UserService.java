package com.maltseva.servicestation.project.service;

import com.maltseva.servicestation.project.dto.*;
import com.maltseva.servicestation.project.exception.UpdateRoleException;
import com.maltseva.servicestation.project.model.*;
import com.maltseva.servicestation.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Maltseva
 * @version 1.0
 * @since 21.12.2022
 */
@Service
public class UserService extends GenericService<User, UserDTO> {

    private static final String CHANGE_PASSWORD_URL = "http://localhost:9090/users/change-password/";
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final PositionRepository positionRepository;
    private final RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;

    private final NamedParameterJdbcTemplate jdbcTemplate;


    public UserService(UserRepository userRepository, CarRepository carRepository,
                       PositionRepository positionRepository, RoleRepository roleRepository,
                       JavaMailSender javaMailSender, NamedParameterJdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.positionRepository = positionRepository;
        this.roleRepository = roleRepository;
        this.javaMailSender = javaMailSender;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User updateFromEntity(User object) {
        return userRepository.save(object);
    }

    @Override
    public User updateFromDTO(UserDTO object, Long objectId) {
        User user = userRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("User with such id = " + objectId + " not found"));
        updateFromUserDTO(object, user);
        userRepository.save(user);
        return user;
    }

    private void updateFromUserDTO(UserDTO object, User user) {
        user.setLastName(object.getLastName());
        user.setFirstName(object.getFirstName());
        user.setMiddleName(object.getMiddleName());
        user.setAddress(object.getAddress());
        user.setDateBirth(object.getDateBirth());
        user.setPhone(object.getPhone());
    }

    /**
     * Изменяем роль сотрудника
     *
     * @param object
     * @param user
     */
    private void updateRole(UserEmployeeDTO object, User user) throws UpdateRoleException {
        Role role;
        if (object.getRole().getId() == 1L) {
            throw new UpdateRoleException("Нельзя изменить роль пользователя. " +
                    "Для сотрудника СТО необходимо создать отдельного пользователя!");
        } else {
            role = roleRepository.findById(object.getRole().getId()).orElseThrow(
                    () -> new NotFoundException("User with such id = " + object.getRole().getId() + " not found"));
        }
        user.setRole(role);
    }

    /**
     * Изменяем данные сотрудника
     *
     * @param object
     * @param objectId
     * @return
     * @throws UpdateRoleException
     */
    public User updateFromUserEmployeeDTO(UserEmployeeDTO object, Long objectId) throws UpdateRoleException {
        User user = userRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("User with such id = " + objectId + " not found"));
        updateFromUserDTO(object, user);
        updateRole(object, user);
        Position position = positionRepository.findById(object.getPositionID()).orElseThrow(
                () -> new NotFoundException("Position with such id = " + object.getPositionID() + " not found"));
        user.setPosition(position);

        User employeeChief = userRepository.findById(object.getEmployeeChiefID()).get();
        user.setChief(employeeChief);

        userRepository.save(user);
        return user;
    }


    @Override
    public User createFromEntity(User newObject) {
        newObject.setPassword(bCryptPasswordEncoder.encode(newObject.getPassword()));
        return userRepository.save(newObject);
    }

    @Override
    public User createFromDTO(UserDTO newObject) {
        User user = new User();
        updateFromUserDTO(newObject, user);

        Role role = roleRepository.findById(1L).orElseThrow(
                () -> new NotFoundException("User with such id = 1 not found"));
        user.setRole(role);

        user.setLogin(newObject.getLogin());
        user.setBackUpEmail(newObject.getBackUpEmail());
        user.setPassword(bCryptPasswordEncoder.encode(newObject.getPassword()));

        user.setCreatedBy("REGISTRATION FORM");
        user.setCreatedWhen(LocalDateTime.now());

        userRepository.save(user);
        return user;
    }

    public User createFromEmployeeDTO(UserEmployeeDTO newObject) {
        User user = new User();
        updateFromUserDTO(newObject, user);

        Role role = roleRepository.findById(newObject.getRole().getId()).orElseThrow(
                () -> new NotFoundException("User with such id = " + newObject.getRole().getId() + " not found"));
        user.setRole(role);

        user.setLogin(newObject.getLogin());
        user.setBackUpEmail(newObject.getBackUpEmail());
        user.setPassword(bCryptPasswordEncoder.encode(newObject.getPassword()));

        user.setCreatedBy("REGISTRATION EMPLOYEE");
        user.setCreatedWhen(LocalDateTime.now());

        userRepository.save(user);
        return user;
    }

    public void delete(Long objectId) {
        User user = userRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("User with such id = " + objectId + " not found"));
        userRepository.delete(user);
    }

    @Override
    public User getOne(Long objectId) {
        return userRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("User with such id = " + objectId + " not found"));
    }


    public UserOwnerCarDTO getOneUserOwnerCarDTO(Long objectId) {
        User user = userRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("User with such id = " + objectId + " not found"));
        UserOwnerCarDTO userOwnerCarDTO = new UserOwnerCarDTO(user);
        Set<Long> carSet = new HashSet<>(carRepository.carIdByUserId(objectId));
        userOwnerCarDTO.setCarSet(carSet);
        return userOwnerCarDTO;
    }

    public UserEmployeeDTO getOneUserEmployeeDTO(Long objectId) {
        User user = userRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("User with such id = " + objectId + " not found"));
        UserEmployeeDTO userEmployeeDTO = new UserEmployeeDTO(user);

        return userEmployeeDTO;
    }

    @Override
    public List<User> listAll() {
        return userRepository.allUsers();
    }

    /**
     * Метод возвращает список автомобилей пользователя
     *
     * @param userId
     * @return List<CarDTO>
     */
    public List<CarDTO> getCarsUser(Long userId) {
        List<CarDTO> cars = new ArrayList<>();
        for (Car car : carRepository.findByUserId(userId)) {
            System.out.println(car.toString());
            CarDTO carDTO = new CarDTO(car);
            cars.add(carDTO);
        }
        return cars;
    }

    /**
     * Почта клиента
     *
     * @param email
     * @return UserDTO
     */
    public UserDTO getUserByEmail(final String email) {
        return new UserDTO(userRepository.findByBackUpEmail(email));
    }

    /**
     * Отправка письма для смены пароля
     *
     * @param backUpEmail
     * @param userId
     */
    public void sendChangePasswordEmail(final String backUpEmail,
                                        final Long userId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(backUpEmail);
        message.setSubject("Восстановление пароля на сервисе \"Онлайн Сервисная книжка\"");
        message.setText("Добрый день! Вы получили это письмо, так как с вашего аккаунта была отправлена заявка на восстановление пароля. " +
                "Для восстановления пароля, перейдите по ссылке: " +
                CHANGE_PASSWORD_URL + userId);
        javaMailSender.send(message);
    }


    /**
     * Изменение пароля
     *
     * @param userId
     * @param password
     */
    public void changePassword(Long userId, String password) {
        System.out.println(userId);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with such ID: " + userId + " not found."));
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
    }

    public User getByUserName(final String name) {
        return userRepository.findUserByLogin(name);
    }


    /**
     * Проверка пароля
     *
     * @param loginDTO
     * @return boolean
     */
    public boolean checkPassword(LoginDTO loginDTO) {
        return bCryptPasswordEncoder.matches(loginDTO.getPassword(),
                getByUserName(loginDTO.getUsername()).getPassword());
    }

    /**
     * Список всех сотрудников в подчинении у данного руководителя по ID руководителя
     *
     * @param chiefId
     * @return List<UserChiefDTO>
     */
    public List<UserChiefDTO> listAllEmployeeByChiefId(Long chiefId) {
        return userRepository.userEmployeeByChiefId(chiefId, jdbcTemplate);
    }

}

