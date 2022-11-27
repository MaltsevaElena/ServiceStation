package com.maltseva.servicestation.project.service;

import com.maltseva.servicestation.project.dto.TariffDTO;
import com.maltseva.servicestation.project.model.ServiceStation;
import com.maltseva.servicestation.project.model.Tariff;
import com.maltseva.servicestation.project.repository.ServiceStationRepository;
import com.maltseva.servicestation.project.repository.TariffRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDateTime.now;

/**
 * @author Maltseva
 * @version 1.0
 * @since 27.11.2022
 */
@Service
public class TariffService extends GenericService<Tariff, TariffDTO> {

    private final TariffRepository tariffRepository;
    private final ServiceStationRepository serviceStationRepository;

    public TariffService(TariffRepository tariffRepository, ServiceStationRepository serviceStationRepository) {
        this.tariffRepository = tariffRepository;
        this.serviceStationRepository = serviceStationRepository;
    }

    @Override
    public Tariff updateFromEntity(Tariff object) {
        return tariffRepository.save(object);
    }

    @Override
    public Tariff updateFromDTO(TariffDTO object, Long objectId) {
        Tariff tariff = tariffRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Tariff with such id = " + objectId + " not found"));

        updateFromTariffDTO(object, tariff);

        return tariffRepository.save(tariff);
    }

    public void updateFromTariffDTO(TariffDTO object, Tariff tariff) {
        tariff.setPrice(object.getPrice());

        ServiceStation serviceStation = serviceStationRepository.findById(object.getServiceStationId()).orElseThrow(
                () -> new NotFoundException("Service Station with such id = " + object.getServiceStationId() + " not found"));
        tariff.setServiceStation(serviceStation);

        tariff.setStartDate(object.getStartDate());
        tariff.setEndDate(object.getEndDate());
    }

    @Override
    public Tariff createFromEntity(Tariff newObject) {
        return tariffRepository.save(newObject);
    }

    @Override
    public Tariff createFromDTO(TariffDTO newObject) {
        Tariff newTariff = new Tariff();

        updateFromTariffDTO(newObject, newTariff);
        newTariff.setCreatedBy(newObject.getCreatedBy());
        newTariff.setCreatedWhen(now());

        return null;
    }

    @Override
    public Tariff getOne(Long objectId) {
        return tariffRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Tariff with such id = " + objectId + " not found"));
    }

    @Override
    public List<Tariff> listAll() {
        return tariffRepository.findAll();
    }

    /**
     * Закрываем действие тарифа.
     *
     * @param tariffId
     * @param date
     * @return
     */
    public Tariff setEndDate(Long tariffId, LocalDate date) {
        Tariff tariff = tariffRepository.findById(tariffId).orElseThrow(
                () -> new NotFoundException("Tariff with such id = " + tariffId + " not found"));
        tariff.setEndDate(date);

        return tariffRepository.save(tariff);
    }

}