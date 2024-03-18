package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.aspect.CheckUsernamePassword;
import com.epam.labtaskspringcore.aspect.LogRestDetails;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.payloads.*;
import com.epam.labtaskspringcore.service.TrainerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@CheckUsernamePassword
@LogRestDetails
@RestController
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    // modify to GET method when I can authorize based on session
    @PostMapping("/trainer-get")
    public ResponseEntity<?> getTrainer(@Valid @RequestBody UsernamePassword usernamePassword) {

        String username = usernamePassword.getUsername();
        Optional<Trainer> trainerOptional = trainerService.getByUsername(username);

        if (trainerOptional.isEmpty()) {
            throw new IllegalArgumentException("no trainer found");
        }
        Trainer trainer = trainerOptional.get();

        TrainerDTOWithTraineeList trainerDTO = getTrainerDTOWithTraineeList(trainer);
        return ResponseEntity.ok().body(trainerDTO);
    }

    @PutMapping("/trainer")
    public ResponseEntity<?> updateTrainer(@Valid @RequestBody TrainerUpdateRequest trainerUpdateRequest) {

        String username = trainerUpdateRequest.getUsername();
        Optional<Trainer> trainerOptional = trainerService.getByUsername(username);
        if (trainerOptional.isEmpty()) {
            throw new IllegalArgumentException("no trainer found");
        }

        Trainer trainer = trainerOptional.get();
        TrainerDTOupdated trainerDTOupdated = getTrainerDTOupdated(trainerUpdateRequest, trainer);

        return ResponseEntity.ok().body(trainerDTOupdated);
    }

    // modify to GET method when I can authorize based on session
    @PostMapping("/trainers/get-not-assigned-to-trainee")
    public ResponseEntity<?> getNotAssignedTrainers(@Valid @RequestBody UsernamePassword usernamePassword) {

        String username = usernamePassword.getUsername();
        String password = usernamePassword.getPassword();
        List<Trainer> trainerList = trainerService.findUnassignedTrainersByTraineeUsername(username, password);

        List<TrainerDTOForTrainersList> trainerDTOs = getTrainerDTOForTrainersLists(trainerList);

        return ResponseEntity.ok().body(trainerDTOs);
    }

    @PatchMapping("/trainer/activate")
    public ResponseEntity<?> activateTrainer(@Valid @RequestBody UsernamePassword usernamePassword) {

        String username = usernamePassword.getUsername();
        Optional<Trainer> trainerOptional = trainerService.getByUsername(username);
        if (trainerOptional.isEmpty()) {
            throw new IllegalArgumentException("no trainer found");
        }
        Trainer trainer = trainerOptional.get();
        trainer.setIsActive(true);
        trainerService.update(trainer);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/trainer/deactivate")
    public ResponseEntity<?> deactivateTrainer(@Valid @RequestBody UsernamePassword usernamePassword) {

        String username = usernamePassword.getUsername();
        Optional<Trainer> trainerOptional = trainerService.getByUsername(username);
        if (trainerOptional.isEmpty()) {
            throw new IllegalArgumentException("no trainer found");
        }
        Trainer trainer = trainerOptional.get();
        trainer.setIsActive(false);
        trainerService.update(trainer);

        return ResponseEntity.ok().build();
    }

    private TrainerDTOWithTraineeList getTrainerDTOWithTraineeList(Trainer trainer) {

        TrainerDTOWithTraineeList trainerDTO = new TrainerDTOWithTraineeList();
        trainerDTO.setFirstName(trainer.getFirstName());
        trainerDTO.setLastName(trainer.getLastName());
        trainerDTO.setSpecialization(trainer.getSpecialization());
        trainerDTO.setIsActive(trainer.getIsActive());

        List<TraineeDTOForTraineesList> trainees;

        if (trainer.getTrainees() != null) {
            trainees = trainer
                    .getTrainees()
                    .stream()
                    .map(trainee -> {
                        TraineeDTOForTraineesList traineeDTO = new TraineeDTOForTraineesList();
                        traineeDTO.setUsername(trainee.getUsername());
                        traineeDTO.setFirstName(trainee.getFirstName());
                        traineeDTO.setLastName(trainee.getLastName());
                        return traineeDTO;
                    }).toList();
        } else {
            trainees = Collections.emptyList();
        }

        Set<TraineeDTOForTraineesList> traineesSet = new HashSet<>(trainees);
        trainerDTO.setTrainees(traineesSet);

        return trainerDTO;
    }

    private TrainerDTOupdated getTrainerDTOupdated(TrainerUpdateRequest trainerUpdateRequest, Trainer trainer) {
        trainer.setFirstName(trainerUpdateRequest.getFirstName());
        trainer.setLastName(trainerUpdateRequest.getLastName());
        trainer.setSpecialization(trainerUpdateRequest.getSpecialization());
        trainer.setIsActive(trainerUpdateRequest.getIsActive());

        Optional<Trainer> updatedTrainerOptional = trainerService.update(trainer);
        if (updatedTrainerOptional.isEmpty()) {
            throw new IllegalArgumentException("no trainer updated");
        }
        Trainer updatedTrainer = updatedTrainerOptional.get();
        TrainerDTOupdated trainerDTOupdated = new TrainerDTOupdated();
        trainerDTOupdated.setUsername(updatedTrainer.getUsername());
        trainerDTOupdated.setFirstName(updatedTrainer.getFirstName());
        trainerDTOupdated.setLastName(updatedTrainer.getLastName());
        trainerDTOupdated.setSpecialization(updatedTrainer.getSpecialization());
        trainerDTOupdated.setIsActive(updatedTrainer.getIsActive());

        Set<Trainee> traineeSet = trainer.getTrainees();
        Set<TraineeDTOForTraineesList> traineeDTOList = null;

        if (traineeSet != null) {
            traineeDTOList = traineeSet
                    .stream()
                    .map(trainee -> {
                        TraineeDTOForTraineesList traineeDTO = new TraineeDTOForTraineesList();
                        traineeDTO.setUsername(trainee.getUsername());
                        traineeDTO.setFirstName(trainee.getFirstName());
                        traineeDTO.setLastName(trainee.getLastName());
                        return traineeDTO;
                    }).collect(Collectors.toSet());
        }
        trainerDTOupdated.setTrainees(traineeDTOList);
        return trainerDTOupdated;
    }

    private List<TrainerDTOForTrainersList> getTrainerDTOForTrainersLists(List<Trainer> trainerList) {
        return trainerList
                .stream()
                .map(trainer -> {
                    TrainerDTOForTrainersList trainerDTO = new TrainerDTOForTrainersList();
                    trainerDTO.setUsername(trainer.getUsername());
                    trainerDTO.setFirstName(trainer.getFirstName());
                    trainerDTO.setLastName(trainer.getLastName());
                    trainerDTO.setSpecialization(trainer.getSpecialization());
                    return trainerDTO;
                }).toList();
    }
}
