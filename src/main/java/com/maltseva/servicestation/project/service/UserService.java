package com.maltseva.servicestation.project.service;

import com.maltseva.servicestation.project.dto.*;
import com.maltseva.servicestation.project.model.Car;
import com.maltseva.servicestation.project.model.Position;
import com.maltseva.servicestation.project.model.ServiceStation;
import com.maltseva.servicestation.project.model.User;
import com.maltseva.servicestation.project.repository.CarRepository;
import com.maltseva.servicestation.project.repository.PositionRepository;
import com.maltseva.servicestation.project.repository.ServiceStationRepository;
import com.maltseva.servicestation.project.repository.UserRepository;
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
    private final ServiceStationRepository serviceStationRepository;
    private final CarRepository carRepository;

    private final PositionRepository positionRepository;

    public UserService(UserRepository userRepository, CarRepository carRepository,
                       ServiceStationRepository serviceStationRepository, PositionRepository positionRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.serviceStationRepository = serviceStationRepository;
        this.positionRepository = positionRepository;
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

        if (object instanceof UserOwnerCarDTO) {
            updateFromUserOwnerCarDTO(object, user);
        }
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

    }

    private void updateFromUserOwnerCarDTO(UserDTO object, User user) {
        Set<Car> carSet = new HashSet<>(carRepository.findAllById(((UserOwnerCarDTO) object).getCarSet()));
        user.setCarSet(carSet);
    }

    private void updateFromUserEmployeeDTO(UserDTO object, User user) {
        Position position = positionRepository.findById(((UserEmployeeDTO) object).getPositionID()).orElseThrow(
                () -> new NotFoundException("Position with such id = " + ((UserEmployeeDTO) object).getPositionID() + " not found")
        );
        user.setPosition(position);

        ServiceStation serviceStation = serviceStationRepository.findById(((UserEmployeeDTO) object).getServiceStationID()).orElseThrow(
                () -> new NotFoundException("ServiceStation with such id = " + ((UserEmployeeDTO) object).getServiceStationID() + " not found"));
        user.setServiceStation(serviceStation);

        User employeeChief = userRepository.findById(((UserEmployeeDTO) object).getEmployeeChiefID()).orElseThrow(
                () -> new NotFoundException("User with such id = " + ((UserEmployeeDTO) object).getEmployeeChiefID() + " not found"));
        user.setChief(employeeChief);
    }

    private void updateFromUserAuthorizationDTO(UserDTO object, User user) {
        user.setBackupEmail(((UserAuthorizationDTO) object).getBackupEmail());
        //    user.setLogin(((UserAuthorizationDTO) object).getLogin()); //Нужно ли изменять логин?
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
        }

        if (newObject instanceof UserOwnerCarDTO) {
            updateFromUserOwnerCarDTO(newObject, user);
        }

        if (newObject instanceof UserEmployeeDTO) {
            updateFromUserEmployeeDTO(newObject, user);
        }

        user.setCreatedBy(newObject.getCreatedBy());
        user.setCreatedWhen(LocalDateTime.now());

        return userRepository.save(user);
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
     * @return
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
