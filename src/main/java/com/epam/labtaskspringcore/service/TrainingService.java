package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.dao.TrainingDAO;
import com.epam.labtaskspringcore.model.Training;
import com.epam.labtaskspringcore.model.TrainingType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TrainingService {
    private TrainingDAO trainingDAO;
    private TrainerDAO trainerDAO;

    public TrainingService(TrainingDAO trainingDAO, TrainerDAO trainerDAO) {
        this.trainingDAO = trainingDAO;
        this.trainerDAO = trainerDAO;
    }

    public Training create(Training training) {
        TrainingType trainingTypeOfTraining = training.getType();
        // .get() without isPresent() check
        TrainingType trainingTypeOfTrainer = trainerDAO.getById(training.getTrainerId()).get().getSpecialization();

        if (!trainingTypeOfTraining.equals(trainingTypeOfTrainer)) {
            log.warn(">>>> cannot create training, because the trainer has a different specialization");
            //            System.out.println("cannot create training, because the trainer has a different
            //            specialization");
            return null;
        } else {
            trainingDAO.create(training);
            log.info(">>>> Creating training: " + training.getName());
            return trainingDAO.getById(training.getId());
        }
    }

    public Training getById(int id) {
        log.info(">>>> Getting training with id: " + id);
        return trainingDAO.getById(id);
    }

    // create/select Training profile
}
