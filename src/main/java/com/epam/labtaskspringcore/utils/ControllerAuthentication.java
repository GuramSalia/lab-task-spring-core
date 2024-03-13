package com.epam.labtaskspringcore.utils;

import com.epam.labtaskspringcore.exception.UnauthorizedException;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

        Optional<Trainee> traineeOptional = traineeService.findByUsernameAndPassword(username, password);
        Optional<Trainer> trainerOptional = trainerService.findByUsernameAndPassword(username, password);

        if (traineeOptional.isEmpty() && trainerOptional.isEmpty()) {
            throw new UnauthorizedException("username or password is incorrect");
        }
    }
}