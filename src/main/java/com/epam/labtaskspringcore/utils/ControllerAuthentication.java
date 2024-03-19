package com.epam.labtaskspringcore.utils;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ControllerAuthentication {

    private final TraineeService traineeService;
    private final TrainerService trainerService;

    @Autowired
    public ControllerAuthentication(TraineeService traineeService, TrainerService trainerService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    public void performAuthentication(String username, String password) {

        Trainee trainee = traineeService.findByUsernameAndPassword(username, password);
        Trainer trainer = trainerService.findByUsernameAndPassword(username, password);

        log.info("start checking with 'performAuthentication'");
    }
}
