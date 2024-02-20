package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.dao.TrainerDAOImpl;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {
    private final TrainerDAO trainerDAO;

    public TrainerService(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    // create/update/select Trainer profile
    public void create(Trainer trainer) {trainerDAO.create(trainer);}
    public void update(Trainer trainer) {trainerDAO.update(trainer);}
    public Trainer select(int id) {return trainerDAO.select(id);}

}
