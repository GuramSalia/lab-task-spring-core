package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.dao.TrainerDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {


    private final TraineeDAO traineeDAO;
    private final TrainerDAO trainerDAO;
    @Autowired
    public UserService(TraineeDAO traineeDAO, TrainerDAO trainerDAO) {
        this.traineeDAO = traineeDAO;
        this.trainerDAO = trainerDAO;
    }






}
