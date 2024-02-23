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
    private TrainingDAO trainingDAO;
    private TrainerDAO trainerDAO;

    public TrainingService(TrainingDAO trainingDAO, TrainerDAO trainerDAO) {
        this.trainingDAO = trainingDAO;
        this.trainerDAO = trainerDAO;
    }

    public Optional<Training> create(Training training) {
        //Assume I can create training without indicating trainerId or trainingType.

        TrainingType trainingTypeOfTraining = training.getType();

        // .get() without isPresent() check

        //        TrainingType trainingTypeOfTrainer = trainerDAO.getById(training.getTrainerId()).get()
        //        .getSpecialization();
        TrainingType trainingTypeOfTrainer;
        Optional<Trainer> optionalTrainer = trainerDAO.getById(training.getTrainerId());
        if (optionalTrainer.isEmpty()) {
            log.warn("There is no such trainer as indicated by training");
            trainingTypeOfTrainer = null;
        } else {
            trainingTypeOfTrainer = optionalTrainer.get().getSpecialization();
        }

        boolean equalTrainingTypesForTrainerAndTraining = false;
        boolean condition1 = trainingTypeOfTraining != null && trainingTypeOfTrainer != null;
        boolean condition2 = trainingTypeOfTraining == null && trainingTypeOfTrainer == null;
        if (condition1) {
            equalTrainingTypesForTrainerAndTraining = trainingTypeOfTraining.equals(trainingTypeOfTrainer);
        } else if (condition2) {
            equalTrainingTypesForTrainerAndTraining = true;
        }

        if (!equalTrainingTypesForTrainerAndTraining) {
            log.error("cannot create training, because the trainer has a different specialization");
            return Optional.empty();
        } else {
            trainingDAO.create(training);
            log.info(">>>> Creating training: " + training.getName());
            return trainingDAO.getById(training.getId());
        }
    }

    public Optional<Training>  getById(int id) {
        log.info(">>>> Getting training with id: " + id);
        return trainingDAO.getById(id);
    }

    // create/select Training profile
}
