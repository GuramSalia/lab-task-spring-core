package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TrainingDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class TrainingService {
    private TrainingDAOImpl trainingDAO;
    @Autowired
    public TrainingService(TrainingDAOImpl trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    // create/select Training profile
}
