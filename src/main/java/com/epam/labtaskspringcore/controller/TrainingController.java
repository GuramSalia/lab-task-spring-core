package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.aspect.CheckUsernamePassword;
import com.epam.labtaskspringcore.aspect.LogRestDetails;
import com.epam.labtaskspringcore.exception.InvalidRequestBodyException;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.Training;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.payloads.TrainingDTO;
import com.epam.labtaskspringcore.payloads.TrainingRegistrationRequest;
import com.epam.labtaskspringcore.payloads.TrainingsByTraineeRequest;
import com.epam.labtaskspringcore.payloads.TrainingsByTrainerRequest;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import com.epam.labtaskspringcore.service.TrainingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@LogRestDetails
@RestController
@CheckUsernamePassword
public class TrainingController {
    private final TrainingService trainingService;
    private final TraineeService traineeService;
    private final TrainerService trainerService;

    @Autowired
    public TrainingController(
            TrainingService trainingService,
            TraineeService traineeService,
            TrainerService trainerService) {
        this.trainingService = trainingService;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    @PostMapping("/training")
    public ResponseEntity<?> registerTraining(
            @Valid @RequestBody TrainingRegistrationRequest trainingRegistrationRequest) {

        Training training = getTraining(trainingRegistrationRequest);
        Optional<Training> createdTrainingOptional = trainingService.create(training);
        if (createdTrainingOptional.isEmpty()) {
            throw new InvalidRequestBodyException("training not created");
        } else {
            return ResponseEntity.ok().build();
        }
    }

    // requirement: 12.	Get Trainee Trainings List (GET method);
    // But we cannot submit body with get method.
    @PostMapping("/training/of-trainee")
    public ResponseEntity<?> getTrainingsByTraineeAndOtherFilters(
            @Valid @RequestBody TrainingsByTraineeRequest trainingsByTraineeRequest) {

        List<Training> trainings = getTrainingsByTrainee(trainingsByTraineeRequest);
        List<TrainingDTO> trainingDTOs = trainings.stream().map(TrainingDTO::new).toList();

        return ResponseEntity.ok().body(trainingDTOs);
    }

    // requirement: 13.	Get Trainer Trainings List (GET method);
    // But we cannot submit body with get method.
    @PostMapping("/training/of-trainer")
    public ResponseEntity<?> getTrainingsByTrainerAndOtherFilters(
            @Valid @RequestBody TrainingsByTrainerRequest trainingsByTrainerRequest) {

        List<Training> trainings = getTrainingsByTrainer(trainingsByTrainerRequest);
        List<TrainingDTO> trainingDTOs = trainings.stream().map(TrainingDTO::new).toList();

        return ResponseEntity.ok().body(trainingDTOs);
    }

    private Training getTraining(TrainingRegistrationRequest trainingRegistrationRequest) {

        String traineeUsername = trainingRegistrationRequest.getTraineeUsername();
        Optional<Trainee> traineeOptional = traineeService.getByUsername(traineeUsername);
        String trainerUsername = trainingRegistrationRequest.getTrainerUsername();
        Optional<Trainer> trainerOptional = trainerService.getByUsername(trainerUsername);

        Training training = new Training();
        if (traineeOptional.isEmpty()) {
            throw new InvalidRequestBodyException("no such trainee");
        } else {
            training.setTrainee(traineeOptional.get());
        }

        if (trainerOptional.isEmpty()) {
            throw new InvalidRequestBodyException("no such trainer");
        } else {
            training.setTrainer(trainerOptional.get());
            training.setTrainingType(trainerOptional.get().getSpecialization());
        }

        String trainingName = trainingRegistrationRequest.getTrainingName();
        Date trainingDate = trainingRegistrationRequest.getTrainingDate();
        int trainingDuration = trainingRegistrationRequest.getTrainingDuration();

        training.setTrainingName(trainingName);
        training.setTrainingDate(trainingDate);
        training.setTrainingDurationInMinutes(trainingDuration);

        return training;
    }

    private List<Training> getTrainingsByTrainee(TrainingsByTraineeRequest trainingsByTraineeRequest) {
        String traineeUsername = trainingsByTraineeRequest.getUsername();
        java.sql.Date periodFrom = trainingsByTraineeRequest.getPeriodFrom();
        java.sql.Date periodTo = trainingsByTraineeRequest.getPeriodTo();
        String trainerUsername = trainingsByTraineeRequest.getTrainerUsername();
        TrainingType trainingType = trainingsByTraineeRequest.getTrainingType();

        String trainingTypeString = trainingType == null ? null : String.valueOf(trainingType.getTrainingType());

        return trainingService.getTrainingsByTraineeAndOtherFilters(
                traineeUsername,
                periodFrom,
                periodTo,
                trainerUsername,
                trainingTypeString);
    }

    private List<Training> getTrainingsByTrainer(TrainingsByTrainerRequest trainingsByTrainerRequest) {
        String trainerUsername = trainingsByTrainerRequest.getUsername();
        java.sql.Date periodFrom = trainingsByTrainerRequest.getPeriodFrom();
        java.sql.Date periodTo = trainingsByTrainerRequest.getPeriodTo();
        String traineeUsername = trainingsByTrainerRequest.getTraineeUsername();

        return trainingService.getTrainingsByTrainerAndOtherFilters(
                trainerUsername,
                periodFrom,
                periodTo,
                traineeUsername);
    }
}
