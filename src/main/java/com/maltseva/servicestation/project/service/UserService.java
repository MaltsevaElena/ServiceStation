package com.maltseva.servicestation.project.service;

import com.maltseva.servicestation.project.dto.*;
import com.maltseva.servicestation.project.model.*;
import com.maltseva.servicestation.project.repository.*;
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

    private final UserRepository userRepository;

    private final CarRepository carRepository;

    private final PositionRepository positionRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, CarRepository carRepository,
                       PositionRepository positionRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.positionRepository = positionRepository;
        this.roleRepository = roleRepository;

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
        if (object instanceof UserAuthorizationDTO) {
            updateFromUserAuthorizationDTO(object, user);
        }
        return userRepository.save(user);
    }

    private void updateFromUserDTO(UserDTO object, User user) {
        user.setLastName(object.getLastName());
        user.setFirstName(object.getFirstName());
        user.setMiddleName(object.getMiddleName());
        user.setAddress(object.getAddress());
        user.setDateBirth(object.getDateBirth());
        user.setPhone(object.getPhone());
        Role role;
        if (object.getRole().getId() == 0 || object.getRole() == null) {
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

        User employeeChief = userRepository.findById(((UserEmployeeDTO) object).getEmployeeChiefID()).orElseThrow(
                () -> new NotFoundException("User with such id = " + ((UserEmployeeDTO) object).getEmployeeChiefID() + " not found"));
        user.setChief(employeeChief);
    }

    private void updateFromUserAuthorizationDTO(UserDTO object, User user) {
        user.setBackupEmail(((UserAuthorizationDTO) object).getBackupEmail());
        user.setPassword(((UserAuthorizationDTO) object).getPassword());
    }


    @Override
    public User createFromEntity(User newObject) {
        return userRepository.save(newObject);
    }

    @Override
    public User createFromDTO(UserDTO newObject) {
        User user = new User();
        updateFromUserDTO(newObject, user);

        if (newObject instanceof UserAuthorizationDTO) {
            updateFromUserAuthorizationDTO(newObject, user);
            user.setLogin(((UserAuthorizationDTO) newObject).getLogin());
        }

        if (newObject instanceof UserEmployeeDTO) {
            updateFromUserEmployeeDTO(newObject, user);
        }

        user.setCreatedBy(newObject.getCreatedBy());
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
}
