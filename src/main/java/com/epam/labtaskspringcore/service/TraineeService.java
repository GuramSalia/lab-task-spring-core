package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAOImpl;
import com.epam.labtaskspringcore.model.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService {
    private TraineeDAOImpl traineeDAO;

    public TraineeService(TraineeDAOImpl traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    public List<Trainee> getTrainees() {
        return traineeDAO.getTrainees();
    }

    // create/update/delete/select Trainee profile
}
