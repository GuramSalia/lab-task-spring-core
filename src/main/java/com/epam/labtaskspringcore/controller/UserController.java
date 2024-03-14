package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.aspect.AuthenticateAspect;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.payloads.PasswordUpdateRequest;
import com.epam.labtaskspringcore.payloads.UsernamePassword;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import com.epam.labtaskspringcore.utils.ControllerAuthentication;
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
public class UserController {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final ControllerAuthentication controllerAuthentication;

    @Autowired
    public UserController(TraineeService traineeService, TrainerService trainerService,
                          ControllerAuthentication controllerAuthentication) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.controllerAuthentication = controllerAuthentication;
    }

    @AuthenticateAspect
    @PostMapping("/user/login")
    public ResponseEntity<?> login(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @Valid @RequestBody UsernamePassword usernamePassword) {


        String username = usernamePassword.getUsername();
        String password = usernamePassword.getPassword();
//        controllerAuthentication.performAuthentication(username, password);
        log.warn("before response");
        return ResponseEntity.ok().build();
    }

    @AuthenticateAspect
    @PutMapping("/user/login")
    public ResponseEntity<?> updatePassword(HttpServletRequest request,
                                            HttpServletResponse response,
                                            @RequestBody PasswordUpdateRequest usernamePassword) {

        String username = usernamePassword.getUsername();
        String currentPassword = usernamePassword.getPassword();
        String newPassword = usernamePassword.getNewPassword();

//        controllerAuthentication.performAuthentication(username, currentPassword);

        Optional<Trainee> traineeOptional = traineeService.findByUsernameAndPassword(username, currentPassword);
        Optional<Trainer> trainerOptional = trainerService.findByUsernameAndPassword(username, currentPassword);

        traineeOptional.ifPresent(trainee -> traineeService.updatePassword(trainee, username, currentPassword, newPassword));
        trainerOptional.ifPresent(trainer -> trainerService.updatePassword(trainer, username, currentPassword, newPassword));

        return ResponseEntity.ok().build();

    }
}
