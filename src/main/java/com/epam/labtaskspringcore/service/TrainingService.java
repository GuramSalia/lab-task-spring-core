package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TrainingDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingService {
    private TrainingDAOImpl trainingDAO;

    public TrainingService(TrainingDAOImpl trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

//    public void createTraining(Training training) {
//        // should I return newly created training for setting fields ?
//
//        trainingDAO.createTraining(training);
//    }



    // create/select Training profile
}
