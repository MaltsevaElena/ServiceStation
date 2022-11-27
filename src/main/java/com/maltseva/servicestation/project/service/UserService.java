package com.maltseva.servicestation.project.service;

import com.maltseva.servicestation.project.dto.UserAuthorizationDTO;
import com.maltseva.servicestation.project.dto.UserDTO;
import com.maltseva.servicestation.project.dto.UserEmployeeDTO;
import com.maltseva.servicestation.project.dto.UserOwnerCarDTO;
import com.maltseva.servicestation.project.model.Car;
import com.maltseva.servicestation.project.model.ServiceStation;
import com.maltseva.servicestation.project.model.User;
import com.maltseva.servicestation.project.repository.CarRepository;
import com.maltseva.servicestation.project.repository.ServiceStationRepository;
import com.maltseva.servicestation.project.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService extends GenericService<User, UserDTO> {

    private final UserRepository userRepository;
    private final ServiceStationRepository serviceStationRepository;
    private final CarRepository carRepository;

    public UserService(UserRepository userRepository, CarRepository carRepository, ServiceStationRepository serviceStationRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.serviceStationRepository = serviceStationRepository;
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

    public void updateFromUserDTO(UserDTO object, User user) {
        user.setLastName(object.getLastName());
        user.setFirstName(object.getFirstName());
        user.setMiddleName(object.getMiddleName());
        user.setAddress(object.getAddress());
        user.setDateBirth(object.getDateBirth());
    }

    public void updateFromUserOwnerCarDTO(UserDTO object, User user) {
        Set<Car> carSet = new HashSet<>(carRepository.findAllById(((UserOwnerCarDTO) object).getCarSet()));
        user.setCarSet(carSet);
    }


    public void updateFromUserEmployeeDTO(UserDTO object, User user) {
        user.setPositionID(((UserEmployeeDTO) object).getPositionID());

        ServiceStation serviceStation = serviceStationRepository.findById(((UserEmployeeDTO) object).getServiceStationID()).orElseThrow(
                () -> new NotFoundException("ServiceStation with such id = " + ((UserEmployeeDTO) object).getServiceStationID() + " not found"));
        user.setServiceStationID(((UserEmployeeDTO) object).getServiceStationID());
        user.setServiceStation(serviceStation);

        User employeeChief = userRepository.findById(((UserEmployeeDTO) object).getEmployeeChiefID()).orElseThrow(
                () -> new NotFoundException("User with such id = " + ((UserEmployeeDTO) object).getEmployeeChiefID() + " not found"));
        user.setEmployeeChiefID(((UserEmployeeDTO) object).getEmployeeChiefID());
        user.setChief(employeeChief);

        Set<User> employeeSet = new HashSet<>(userRepository.findAllById(((UserEmployeeDTO) object).getEmployeeSet()));
        user.setEmployeeSet(employeeSet);
    }

    public void updateFromUserAuthorizationDTO(UserDTO object, User user) {
        user.setBackupEmail(((UserAuthorizationDTO) object).getBackupEmail());
        user.setLogin(((UserAuthorizationDTO) object).getLogin()); //Нужно ли изменять логин?
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
        updateFromUserAuthorizationDTO(newObject, user);

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

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }
}
