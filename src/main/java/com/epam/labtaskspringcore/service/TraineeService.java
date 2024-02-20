package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.dao.TraineeDAOImpl;
import com.epam.labtaskspringcore.model.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService {
    private final TraineeDAO traineeDAO;

    public TraineeService(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    // create/update/delete/select Trainee profile
    public void create(Trainee trainee) {traineeDAO.create(trainee);}

    public void update(Trainee trainee) {traineeDAO.update(trainee);}

    public void delete(int traineeId) {traineeDAO.delete(traineeId);}

    public Trainee select(int id) {return traineeDAO.select(id);}
}
