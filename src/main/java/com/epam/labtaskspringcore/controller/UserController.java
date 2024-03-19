package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.aspect.CheckUsernamePassword;
import com.epam.labtaskspringcore.aspect.LogRestDetails;
import com.epam.labtaskspringcore.exception.UserNotFoundException;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.payloads.PasswordUpdateRequest;
import com.epam.labtaskspringcore.payloads.UsernamePassword;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CheckUsernamePassword
@LogRestDetails
public class UserController {

    private final TraineeService traineeService;
    private final TrainerService trainerService;

    @Autowired
    public UserController(TraineeService traineeService, TrainerService trainerService ) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@Valid @RequestBody UsernamePassword usernamePassword) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/login")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordUpdateRequest usernamePassword) {

        String username = usernamePassword.getUsername();
        String currentPassword = usernamePassword.getPassword();
        String newPassword = usernamePassword.getNewPassword();

        Trainee trainee = null;
        Trainer trainer = null;

        boolean isIdentified = false;

        try {
            trainee = traineeService.findByUsernameAndPassword(username, currentPassword);
            trainer = trainerService.findByUsernameAndPassword(username, currentPassword);

            if (trainee != null) {
                traineeService.updatePassword(trainee, username, currentPassword, newPassword);
                isIdentified = true;
            }

            if (trainer != null) {
                trainerService.updatePassword(trainer, username, currentPassword, newPassword);
                isIdentified = true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (!isIdentified) {
            throw new UserNotFoundException("no such user");
        }


        return ResponseEntity.ok().build();
    }
}
