package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.payloads.PasswordUpdateRequest;
import com.epam.labtaskspringcore.payloads.UsernamePassword;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    private final TraineeService traineeService;
    private final TrainerService trainerService;

    @Autowired
    public UserController(TraineeService traineeService, TrainerService trainerService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    @GetMapping("/user/login")
    public ResponseEntity<?> login(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestBody UsernamePassword usernamePassword) {

        //     a.	Request
        //        I.	Username (required)
        //        II.    Password (required)
        //     b.	Response
        //        I.	200 OK

        return null;
    }

    @PutMapping("/user/login")
    public ResponseEntity<?> updatePassword(HttpServletRequest request,
                                            HttpServletResponse response,
                                            @RequestBody PasswordUpdateRequest usernamePassword) {

        //   a.	Request
        //        I.	Username (required)
        //        II.	Old Password (required)
        //        III.	New Password (required)
        //   b.	Response
        //        I.	200 OK

        Optional<Trainee> trainee = traineeService.findByUsernameAndPassword(usernamePassword.getUsername(),
                                                                             usernamePassword.getOldPassword());
        Optional<Trainer> trainer = trainerService.findByUsernameAndPassword(usernamePassword.getUsername(),
                                                                             usernamePassword.getOldPassword());
        Boolean success = false;

        if (trainee.isPresent()) {
            traineeService.updatePassword(
                    trainee.get(),
                    usernamePassword.getUsername(),
                    usernamePassword.getOldPassword(),
                    usernamePassword.getNewPassword());

            success = true;
        }
        if (trainer.isPresent()) {
            trainerService.updatePassword(
                    trainer.get(),
                    usernamePassword.getUsername(),
                    usernamePassword.getOldPassword(),
                    usernamePassword.getNewPassword());

            success = true;
        }

        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
