package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.aspect.CheckUsernamePassword;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.payloads.PasswordUpdateRequest;
import com.epam.labtaskspringcore.payloads.UsernamePassword;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@CheckUsernamePassword
public class UserController {

    private final TraineeService traineeService;
    private final TrainerService trainerService;

    @Autowired
    public UserController(TraineeService traineeService, TrainerService trainerService
                         ) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }


    @PostMapping("/user/login")
    public ResponseEntity<?> login(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @Valid @RequestBody UsernamePassword usernamePassword) {

        log.info("POST /user/login called");
        return ResponseEntity.ok().build();
    }


    @PutMapping("/user/login")
    public ResponseEntity<?> updatePassword(HttpServletRequest request,
                                            HttpServletResponse response,
                                            @Valid @RequestBody PasswordUpdateRequest usernamePassword) {

        log.info("PUT /user/login called");

        String username = usernamePassword.getUsername();
        String currentPassword = usernamePassword.getPassword();
        String newPassword = usernamePassword.getNewPassword();

        Optional<Trainee> traineeOptional = traineeService.findByUsernameAndPassword(username, currentPassword);
        Optional<Trainer> trainerOptional = trainerService.findByUsernameAndPassword(username, currentPassword);

        traineeOptional
                .ifPresent(trainee -> traineeService.updatePassword(trainee, username, currentPassword, newPassword));
        trainerOptional
                .ifPresent(trainer -> trainerService.updatePassword(trainer, username, currentPassword, newPassword));

        return ResponseEntity.ok().build();
    }
}
