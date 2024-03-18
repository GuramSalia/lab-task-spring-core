package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.aspect.LogRestDetails;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.payloads.TraineeRegistrationRequest;
import com.epam.labtaskspringcore.payloads.TrainerRegistrationRequest;
import com.epam.labtaskspringcore.payloads.UsernamePassword;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import com.epam.labtaskspringcore.service.TrainingTypeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@LogRestDetails
@RestController
public class CreateUserController {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingTypeService trainingTypeService;

    public CreateUserController(
            TraineeService traineeService,
            TrainerService trainerService,
            TrainingTypeService trainingTypeService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingTypeService = trainingTypeService;
    }

    @PostMapping("/trainee")
    public ResponseEntity<?> registerTrainee(@Valid @RequestBody TraineeRegistrationRequest traineeRegistrationRequest) {

        Trainee newTrainee = getTrainee(traineeRegistrationRequest);
        Optional<Trainee> traineeOptional = traineeService.create(newTrainee);

        if (traineeOptional.isPresent()) {
            Trainee trainee = traineeOptional.get();
            UsernamePassword usernamePassword = new UsernamePassword(trainee.getUsername(), trainee.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(usernamePassword);
        } else {
            throw new RuntimeException("Couldn't create trainee");
        }
    }

    private static Trainee getTrainee(TraineeRegistrationRequest traineeRegistrationRequest) {
        Trainee newTrainee = new Trainee();
        newTrainee.setFirstName(traineeRegistrationRequest.getFirstName());
        newTrainee.setLastName(traineeRegistrationRequest.getLastName());
        newTrainee.setAddress(traineeRegistrationRequest.getAddress());
        newTrainee.setDob(traineeRegistrationRequest.getDateOfBirth());
        newTrainee.setIsActive(true);
        return newTrainee;
    }

    @PostMapping("/trainer")
    public ResponseEntity<?> registerTrainer(@Valid @RequestBody TrainerRegistrationRequest trainerRegistrationRequest) {

        Optional<Trainer> trainerOptional = getTrainer(trainerRegistrationRequest);

        if (trainerOptional.isPresent()) {

            Trainer trainer = trainerOptional.get();
            UsernamePassword usernamePassword = new UsernamePassword(trainer.getUsername(), trainer.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(usernamePassword);
        } else {
            throw new RuntimeException("Couldn't create trainer");
        }
    }

    private Optional<Trainer> getTrainer(TrainerRegistrationRequest trainerRegistrationRequest) {
        Trainer newTrainer = new Trainer();
        newTrainer.setFirstName(trainerRegistrationRequest.getFirstName());
        newTrainer.setLastName(trainerRegistrationRequest.getLastName());

        TrainingType.TrainingTypeEnum typeEnum =
                TrainingType.TrainingTypeEnum.valueOf(trainerRegistrationRequest.getSpecialization());

        TrainingType specialization =
                trainingTypeService.getTrainingType(typeEnum);
        newTrainer.setSpecialization(specialization);
        newTrainer.setIsActive(true);

        return trainerService.create(newTrainer);
    }
}
