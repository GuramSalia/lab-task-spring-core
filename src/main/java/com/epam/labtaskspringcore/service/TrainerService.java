package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TrainerDAOImpl;
import com.epam.labtaskspringcore.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {
    private TrainerDAOImpl trainerDAO;


    public TrainerService(TrainerDAOImpl trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    public List<Trainer> getTrainers() {
        return trainerDAO.getTrainers();
    }

    // create/update/select Trainer profile
}
