package com.maltseva.servicestation.project.service;

import com.maltseva.servicestation.project.dto.*;
import com.maltseva.servicestation.project.model.*;
import com.maltseva.servicestation.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 13.12.2022
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

    public UserService(UserRepository userRepository, CarRepository carRepository,
                       PositionRepository positionRepository, RoleRepository roleRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.positionRepository = positionRepository;
        this.roleRepository = roleRepository;

        this.javaMailSender = javaMailSender;
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

        if (object instanceof UserEmployeeDTO) {
            updateFromUserEmployeeDTO(object, user);
        }

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
        Role role;
        if (object.getRole() == null) {
            role = roleRepository.findById(1L).get();
        } else {
            role = roleRepository.findById(object.getRole().getId()).get();
        }
        user.setRole(role);
    }


    private void updateFromUserEmployeeDTO(UserDTO object, User user) {
        Position position = positionRepository.findById(((UserEmployeeDTO) object).getPositionID()).orElseThrow(
                () -> new NotFoundException("Position with such id = " + ((UserEmployeeDTO) object).getPositionID() + " not found")
        );
        user.setPosition(position);

        User employeeChief = userRepository.findById(((UserEmployeeDTO) object).getEmployeeChiefID()).get();
        user.setChief(employeeChief);
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
        user.setLogin(newObject.getLogin());
        user.setBackUpEmail(newObject.getBackUpEmail());
        user.setPassword(bCryptPasswordEncoder.encode(newObject.getPassword()));


        if (newObject instanceof UserEmployeeDTO) {
            updateFromUserEmployeeDTO(newObject, user);
        }

        user.setCreatedBy("REGISTRATION FORM");
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
     * Восстановление пароля по почте
     *
     * @param email
     * @return UserDTO
     */
    public UserDTO getUserByEmail(final String email) {
        return new UserDTO(userRepository.findByBackUpEmail(email));
    }

    public void sendChangePasswordEmail(final String backUpEmail,
                                        final Long userId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(backUpEmail);
        message.setSubject("Восстановление пароля на сервисе \"Онлайн Сервисная книжка\"");
        message.setText("Добрый день! Вы получили это письмо, так как с вашего аккаунта была отправлена заявка на восстановление пароля. " +
                "Для восстановления пароля, перейдите по ссылке: " + CHANGE_PASSWORD_URL + userId);
        javaMailSender.send(message);

    }


    public void changePassword(Long userId, String password) {
        System.out.println(userId);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with such ID: " + userId + " not found."));
        System.out.println(password);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
    }

    public User getByUserName(final String name) {
        return userRepository.findUserByLogin(name);
    }


    public boolean checkPassword(LoginDTO loginDTO) {
        return bCryptPasswordEncoder.matches(loginDTO.getPassword(),
                getByUserName(loginDTO.getUsername()).getPassword());
    }
}

