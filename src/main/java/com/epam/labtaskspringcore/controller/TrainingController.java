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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @RequestBody TrainingRegistrationRequest trainingRegistrationRequest) {

        //   a.	Request
        //        I added username and password as requested
        //        I.	Trainee username (required)
        //        II.	Trainer username (required)
        //        III.	Training Name (required)
        //        IV.	Training Date (required)
        //        V.	Training Duration (required)
        //    b.	Response
        //        I.	200 OK

        Training training = getTraining(trainingRegistrationRequest);
        trainingService.create(training);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/training/of-trainee")
    public ResponseEntity<?> getTrainingsByTraineeAndOtherFilters(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @RequestBody TrainingsByTraineeRequest trainingsByTraineeRequest) {

        //    a.	Request
        //        I.    Password (required)
        //        I.	Username (required)
        //        II.	Period From (optional)
        //        III.	Period To (optional)
        //        IV.	Trainer Name (optional)
        //        V.	Training Type (optional)
        //    b.	Response
        //        I.	Training Name
        //        II.	Training Date
        //        III.	Training Type
        //        IV.	Training Duration
        //        V.	Trainer Name

        List<Training> trainings = getTrainingsByTrainee(trainingsByTraineeRequest);
        List<TrainingDTO> trainingDTOs = trainings.stream().map(TrainingDTO::new).toList();

        return ResponseEntity.ok().body(trainingDTOs);
    }

    @PostMapping("/training/of-trainer")
    public ResponseEntity<?> getTrainingsByTrainerAndOtherFilters(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @RequestBody TrainingsByTrainerRequest trainingsByTrainerRequest) {

        //    a.	Request
        //        I.	Username (required)
        //        II.   Password (required)
        //        II.	Period From (optional)
        //        III.	Period To (optional)
        //        IV.	Trainee Name (optional)
        //    b.	Response
        //        I.	Training Name
        //        II.	Training Date
        //        III.	Training Type
        //        IV.	Training Duration
        //        V.	Trainee Name

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
        }

        String trainingName = trainingRegistrationRequest.getTrainingName();
        Date trainingDate = trainingRegistrationRequest.getTraingDate();
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
