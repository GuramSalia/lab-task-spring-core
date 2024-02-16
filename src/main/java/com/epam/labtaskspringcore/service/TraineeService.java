package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class TraineeService {
    private TraineeDAOImpl traineeDAO;
    @Autowired
    public TraineeService(TraineeDAOImpl traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    // create/update/delete/select Trainee profile

}
