package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Training;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.*;

@Slf4j
@Repository("TRAINING_IN_MEMORY")
@Primary
public class TrainingDAOInMemoryStorageImpl implements TrainingDAO {
    private final Map<Integer, Training> trainings;

    public TrainingDAOInMemoryStorageImpl(InMemoryStorage storage) {this.trainings = storage.getTrainings();}

    @Override
    public Optional<Training> create(Training training) {
        int id = training.getTrainingId();
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

    @Override
    public List<Training> getTrainingsByTraineeAndOtherFilters(String traineeUsername, Date startDate, Date endDate, String trainerUsername, String trainingTypeName) {
        log.info("Not implemented with In Memory Storage");
        return null;
    }

    @Override
    public List<Training> getTrainingsByTrainerAndOtherFilters(String traineeUsername, Date startDate, Date endDate, String trainerUsername) {
        log.info("Not implemented with In Memory Storage");
        return null;
    }
}
