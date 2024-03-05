package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.dao.TrainingDAO;
import com.epam.labtaskspringcore.model.Trainee;
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
        TrainingType trainingType = training.getTrainingType();
        TrainingType trainerSpecialization;
        Optional<Trainer> optionalTrainer = trainerDAO.getById(training.getTrainer().getUserId());

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
            log.info(">>>> Creating training: " + training.getTrainingName());
            return trainingDAO.getById(training.getTrainingId());
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

    public Training createTraining(int trainingId, Trainee trainee, Trainer trainer, String trainingName, TrainingType trainingType, int trainingDurationInMinutes) {
        Training training = new Training();
        training.setTrainingId(trainingId);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingName(trainingName);
        training.setTrainingType(trainingType);
        training.setTrainingDurationInMinutes(trainingDurationInMinutes);
        return training;
    }

    public void logTrainingCreationDetails(Training training) {
        if (create(training).isEmpty()) {
            log.error("could not create training with trainingId: " + training.getTrainingId());
        } else {
            Optional<Training> training3Optional = getById(training.getTrainingId());
            if (training3Optional.isEmpty()) {
                log.error("could not get training with trainingId: " + training.getTrainingId());
            } else {
                log.info("created successfully: " + training3Optional.get().toString());
            }
        }
    }
}
