package com.maltseva.servicestation.project.service;


import com.maltseva.servicestation.project.dto.DiagnosticSheetDTO;
import com.maltseva.servicestation.project.model.*;
import com.maltseva.servicestation.project.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

/**
 * @author Maltseva
 * @version 1.0
 * @since 23.12.2022
 */
@Service
@Slf4j
public class DiagnosticSheetService extends GenericService<DiagnosticSheet, DiagnosticSheetDTO> {

    private static final String UPLOAD_DIRECTORY = "files/cars";

    private final DiagnosticSheetRepository diagnosticSheetRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final PositionRepository positionRepository;

    private final ServiceStationRepository serviceStationRepository;

    public DiagnosticSheetService(DiagnosticSheetRepository diagnosticSheetRepository,
                                  CarRepository carRepository,
                                  UserRepository userRepository,
                                  PositionRepository positionRepository,
                                  ServiceStationRepository serviceStationRepository) {
        this.diagnosticSheetRepository = diagnosticSheetRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.positionRepository = positionRepository;
        this.serviceStationRepository = serviceStationRepository;
    }
    @Override
    public DiagnosticSheet updateFromEntity(DiagnosticSheet object) {
        return diagnosticSheetRepository.save(object);
    }
    @Override
    public DiagnosticSheet updateFromDTO(DiagnosticSheetDTO object, Long objectId) {
        DiagnosticSheet diagnosticSheet = diagnosticSheetRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Diagnostic sheet with such id = " + objectId + " not found"));
        diagnosticSheet.setRepairDate(object.getRepairDate());
        return diagnosticSheetRepository.save(diagnosticSheet);
    }
    @Override
    public DiagnosticSheet createFromEntity(DiagnosticSheet newObject) {
        newObject.setCreatedBy(newObject.getCreatedBy());
        newObject.setCreatedWhen(newObject.getCreatedWhen());
        return diagnosticSheetRepository.save(newObject);
    }

    @Override
    public DiagnosticSheet createFromDTO(DiagnosticSheetDTO newObject) {
        DiagnosticSheet diagnosticSheet = new DiagnosticSheet();
        diagnosticSheet.setRepairDate(newObject.getRepairDate());

        Car car = carRepository.findById(newObject.getCarId()).orElseThrow(
                () -> new NotFoundException("Car with such id = " + newObject.getCarId() + " not found"));
        diagnosticSheet.setCar(car);

        User employer = userRepository.findById(newObject.getEmployerId()).orElseThrow(
                () -> new NotFoundException("User with such id = " + newObject.getEmployerId() + "not found"));
        diagnosticSheet.setEmployer(employer);

        diagnosticSheet.setCreatedBy(employer.getPosition().getServiceStation().getName());
        diagnosticSheet.setCreatedWhen(newObject.getCreatedWhen());

        return diagnosticSheetRepository.save(diagnosticSheet);
    }

    @Override
    public DiagnosticSheet getOne(Long objectId) {
        return diagnosticSheetRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Diagnostic sheet with such id = " + objectId + " not found"));
    }
    @Override
    public List<DiagnosticSheet> listAll() {
        return diagnosticSheetRepository.allDiagnosticSheet();
    }

    public void delete(Long objectId) {
        DiagnosticSheet diagnosticSheet = diagnosticSheetRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Diagnostic sheet with such id = " + objectId + " not found"));
        diagnosticSheetRepository.delete(diagnosticSheet);
    }

    /**
     * Возвращает полную информацию  о листе диагностики
     * @param objectId
     * @return DiagnosticSheetDTO
     */

    public DiagnosticSheetDTO getOneDiagnosticSheetDTO(Long objectId) {
        DiagnosticSheet diagnosticSheet = diagnosticSheetRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Diagnostic sheet with such id = " + objectId + " not found"));
        DiagnosticSheetDTO diagnosticSheetDTO = new DiagnosticSheetDTO(diagnosticSheet);

        Car car = carRepository.findById(diagnosticSheet.getCar().getId()).orElseThrow(
                () -> new NotFoundException("Car with such id = " + diagnosticSheet.getCar().getId() + " not found"));
        diagnosticSheetDTO.setCarId(car.getId());
        diagnosticSheetDTO.setRegistrationNumber(car.getRegistrationNumber());
        diagnosticSheetDTO.setVin(car.getVin());
        diagnosticSheetDTO.setMileage(car.getMileage());

        User user = userRepository.userByCarId(diagnosticSheet.getCar().getId()).orElseThrow(
                () -> new NotFoundException("User of the car with such id = " + diagnosticSheet.getCar().getId() + " not found"));
        diagnosticSheetDTO.setLastNameOwnerCar(user.getLastName());
        diagnosticSheetDTO.setFirstNameOwnerCar(user.getFirstName());
        diagnosticSheetDTO.setMiddleNameOwnerCar(user.getMiddleName());
        diagnosticSheetDTO.setPhoneOwnerCar(user.getPhone());
        diagnosticSheetDTO.setEmailOwnerCar(user.getLogin());

        User employer = userRepository.findById(diagnosticSheet.getEmployer().getId()).orElseThrow(
                () -> new NotFoundException("User employer of the car with such id = " + diagnosticSheet.getEmployer().getId() + " not found"));

        diagnosticSheetDTO.setEmployerId(employer.getId());
        diagnosticSheetDTO.setLastNameEmployer(employer.getLastName());
        diagnosticSheetDTO.setFirstNameEmployer(employer.getFirstName());
        diagnosticSheetDTO.setMiddleNameEmployer(employer.getMiddleName());

        Position position = positionRepository.findById(diagnosticSheet.getEmployer().getPosition().getId()).orElseThrow(
                () -> new NotFoundException("Position with such id = " + diagnosticSheet.getEmployer().getPosition().getId() + " not found"));
        diagnosticSheetDTO.setNamePosition(position.getName());

        ServiceStation serviceStation = serviceStationRepository.findById(position.getServiceStation().getId()).orElseThrow(
                () -> new NotFoundException("Service station with such id = " + position.getServiceStation().getId()));

        diagnosticSheetDTO.setNameServiceStation(serviceStation.getName());
        diagnosticSheetDTO.setAddressServiceStation(serviceStation.getAddress());

        return diagnosticSheetDTO;
    }

    /**
     * Возвращает список Id всех Листов диагностики по ID автомобиля
     *
     * @param carId
     * @return List<Long>
     */
    public List<Long> diagnosticSheetByCar(Long carId) {
        List<Long> diagnosticSheetList = diagnosticSheetRepository.allDiagnosticSheetByCarId(carId);
        return diagnosticSheetList;
    }

    /**
     * Добавляет файл в директорию
     *
     * @param file
     * @param id
     * @return String
     */
    public String loadFile(MultipartFile file, Long id) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        System.out.println(fileName);
        String resultFileName = "";
        DiagnosticSheet diagnosticSheet = diagnosticSheetRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Diagnostic sheet with such id = " + id + " not found"));
        try {
            Path path = Paths.get(UPLOAD_DIRECTORY + "/" + diagnosticSheet.getCar().getId() + "/" + fileName).toAbsolutePath().normalize();
            if (!path.toFile().exists()) {
                Files.createDirectories(path);
            }
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            resultFileName = UPLOAD_DIRECTORY + "/" + diagnosticSheet.getCar().getId() + "/" + fileName;
            diagnosticSheet.setDiagnosticResult(resultFileName);
            System.out.println(diagnosticSheet.getDiagnosticResult());
            diagnosticSheetRepository.save(diagnosticSheet);
        } catch (IOException e) {
            log.error("diagnosticSheetService#createFile(): {}", e.getMessage());
        }
        return resultFileName;
    }
}
