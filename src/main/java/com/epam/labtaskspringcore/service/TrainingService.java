package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TrainingDAOImpl;
import com.epam.labtaskspringcore.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingService {
    private TrainingDAOImpl trainingDAO;

    public TrainingService(TrainingDAOImpl trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    public Training create(Training training) {
        trainingDAO.create(training);
        return trainingDAO.getById(training.getTrainingId());
    }

    public Training getById(int id) {
        return trainingDAO.getById(id);
    }


    // create/select Training profile
}
