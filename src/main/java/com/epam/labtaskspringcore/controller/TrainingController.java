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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Operation(summary = "Register Training")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Training registered successfully")
    })
    public ResponseEntity<?> registerTraining(
            @Valid @RequestBody TrainingRegistrationRequest trainingRegistrationRequest) {

        Training training = getTraining(trainingRegistrationRequest);
        Training createdTraining = trainingService.create(training);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // requirement: 12.	Get Trainee Trainings List (GET method);
    // But we cannot submit body with get method.
    @PostMapping("/training/of-trainee")
    @Operation(summary = "Get Trainings by Trainee and optionally by period from, period to, trainer name, training " +
            "type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainings")
    })
    public ResponseEntity<?> getTrainingsByTraineeAndOtherFilters(
            @Valid @RequestBody TrainingsByTraineeRequest trainingsByTraineeRequest) {

        List<Training> trainings = getTrainingsByTrainee(trainingsByTraineeRequest);
        List<TrainingDTO> trainingDTOs = trainings.stream().map(TrainingDTO::new).toList();

        return ResponseEntity.ok().body(trainingDTOs);
    }

    // requirement: 13.	Get Trainer Trainings List (GET method);
    // But we cannot submit body with get method.
    @PostMapping("/training/of-trainer")
    @Operation(summary = "Get Trainings by Trainer and optionally by period from, period to, trainee name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainings")
    })
    public ResponseEntity<?> getTrainingsByTrainerAndOtherFilters(
            @Valid @RequestBody TrainingsByTrainerRequest trainingsByTrainerRequest) {

        List<Training> trainings = getTrainingsByTrainer(trainingsByTrainerRequest);
        List<TrainingDTO> trainingDTOs = trainings.stream().map(TrainingDTO::new).toList();

        return ResponseEntity.ok().body(trainingDTOs);
    }

    private Training getTraining(TrainingRegistrationRequest trainingRegistrationRequest) {

        String traineeUsername = trainingRegistrationRequest.getTraineeUsername();
        Trainee trainee = traineeService.findByUsername(traineeUsername);
        String trainerUsername = trainingRegistrationRequest.getTrainerUsername();
        Trainer trainer = trainerService.findByUsername(trainerUsername);

        Training training = new Training();
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingType(trainer.getSpecialization());

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
