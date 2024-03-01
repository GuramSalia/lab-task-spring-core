package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Training;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class TrainingDAOImpl implements TrainingDAO {
    private final Map<Integer, Training> trainings;

    public TrainingDAOImpl(InMemoryStorage storage) {this.trainings = storage.getTrainings();}

    @Override
    public Optional<Training> create(Training training) {
        int id = training.getId();
        if (trainings.containsKey(id)) {
            log.error("Training with id {} already exists", id);
            return Optional.empty();
        }
        trainings.put(id, training);
        return Optional.of(trainings.get(id));
    }

    @Override
    public Optional<Training> getById(int id) {
        return Optional.ofNullable(trainings.get(id));
    }

    @Override
    public List<Training> getTrainings() {return new ArrayList<>(trainings.values());}
}
