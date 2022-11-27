package com.maltseva.servicestation.project.service;

import com.maltseva.servicestation.project.dto.PositionDTO;
import com.maltseva.servicestation.project.model.Position;
import com.maltseva.servicestation.project.repository.PositionRepository;
import com.maltseva.servicestation.project.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class PositionService extends GenericService<Position, PositionDTO> {

    private final PositionRepository positionRepository;
    private final UserRepository userRepository;

    public PositionService(PositionRepository positionRepository, UserRepository userRepository) {
        this.positionRepository = positionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Position updateFromEntity(Position object) {
        return positionRepository.save(object);
    }

    @Override
    public Position updateFromDTO(PositionDTO object, Long objectId) {
        Position position = positionRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Position with such id = " + objectId + " not found"));
        updateFromPositionDTO(object, position);
        return positionRepository.save(position);
    }

    public void updateFromPositionDTO(PositionDTO object, Position position) {
        position.setDescription(object.getDescription());
        position.setTitle(object.getTitle());
    }

    @Override
    public Position createFromEntity(Position newObject) {
        return positionRepository.save(newObject);
    }

    @Override
    public Position createFromDTO(PositionDTO newObject) {
        Position newPosition = new Position();
        updateFromPositionDTO(newObject, newPosition);

        return positionRepository.save(newPosition);
    }

    @Override
    public Position getOne(Long objectId) {
        return positionRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Position with such id = " + objectId + " not found"));
    }

    @Override
    public List<Position> listAll() {
        return positionRepository.findAll();
    }
}
