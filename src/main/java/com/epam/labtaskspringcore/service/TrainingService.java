package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.dao.TrainingDAO;
import com.epam.labtaskspringcore.model.Training;
import com.epam.labtaskspringcore.model.TrainingType;
import org.springframework.stereotype.Service;

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
        TrainingType trainingTypeOfTrainer = trainerDAO.getById(training.getTrainerId()).getSpecialization();

        if (!trainingTypeOfTraining.equals(trainingTypeOfTrainer)) {
            System.out.println("cannot create training, because the trainer has a different specialization");
            return null;
        } else {
            trainingDAO.create(training);
            return trainingDAO.getById(training.getTrainingId());
        }

    }

    public Training getById(int id) {
        return trainingDAO.getById(id);
    }


    // create/select Training profile
}
