package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TrainerDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class TrainerService {
    private TrainerDAOImpl trainerDAO;

    @Autowired
    public TrainerService(TrainerDAOImpl trainerDAO) {
        this.trainerDAO = trainerDAO;
    }


    // create/update/select Trainer profile
}
