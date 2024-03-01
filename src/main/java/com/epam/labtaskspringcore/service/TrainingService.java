package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.dao.TrainingDAO;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.Training;
import com.epam.labtaskspringcore.model.TrainingType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class TrainingService {
    private final TrainingDAO trainingDAO;
    private final TrainerDAO trainerDAO;

    public TrainingService(TrainingDAO trainingDAO, TrainerDAO trainerDAO) {
        this.trainingDAO = trainingDAO;
        this.trainerDAO = trainerDAO;
    }

    public Optional<Training> create(Training training) {
        TrainingType trainingType = training.getType();
        TrainingType trainerSpecialization;
        Optional<Trainer> optionalTrainer = trainerDAO.getById(training.getTrainerId());

        if (optionalTrainer.isEmpty()) {
            log.warn("There is no such trainer as indicated by training");
            trainerSpecialization = null;
        } else {
            trainerSpecialization = optionalTrainer.get().getSpecialization();
        }

        if (!areTrainingTypesMatching(trainingType, trainerSpecialization)) {
            log.error("cannot create training, because the trainer has a different specialization");
            return Optional.empty();
        } else {
            trainingDAO.create(training);
            log.info(">>>> Creating training: " + training.getName());
            return trainingDAO.getById(training.getId());
        }
    }

    public Optional<Training> getById(int id) {
        log.info(">>>> Getting training with id: " + id);
        return trainingDAO.getById(id);
    }

    private boolean areTrainingTypesMatching(TrainingType type1, TrainingType type2) {
        boolean matching = false;
        if (type1 != null && type2 != null) {
            matching = type1.equals(type2);
        } else if (type1 == null && type2 == null) {
            matching = true;
        }
        return matching;
    }
}
