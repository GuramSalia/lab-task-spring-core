package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.aspect.CheckUsernamePassword;
import com.epam.labtaskspringcore.aspect.LogRestDetails;
import com.epam.labtaskspringcore.exception.InvalidRequestBodyException;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.payloads.*;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CheckUsernamePassword
@LogRestDetails
@RestController
public class TraineeController {
    private final TraineeService traineeService;
    private final TrainerService trainerService;

    public TraineeController(
            TraineeService traineeService,
            TrainerService trainerService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    // modify to GET method when I can authorize based on session
    @PostMapping("/trainee-get")
    public ResponseEntity<?> getTrainee(@Valid @RequestBody UsernamePassword usernamePassword) {

        String username = usernamePassword.getUsername();
        String password = usernamePassword.getPassword();

        Optional<Trainee> traineeOptional = traineeService.findByUsernameAndPassword(username, password);
        if (traineeOptional.isEmpty()) {
            throw new InvalidRequestBodyException("Could not find trainee");
        }

        Trainee trainee = traineeOptional.get();
        TraineeDTOWithTrainersList traineeDTO = getTraineeDTOWithTrainersList(trainee);

        return ResponseEntity.ok(traineeDTO);
    }

    @PutMapping("/trainee")
    public ResponseEntity<?> updateTrainee(@Valid @RequestBody TraineeUpdateRequest traineeUpdateRequest) {

        String username = traineeUpdateRequest.getUsername();
        String password = traineeUpdateRequest.getPassword();
        Optional<Trainee> traineeOptional = traineeService.findByUsernameAndPassword(username, password);

        if (traineeOptional.isEmpty()) {
            throw new InvalidRequestBodyException("username or password is invalid");
        }

        Trainee trainee = traineeOptional.get();
        TraineeDTOUpdated traineeDTO = getTraineeDTOUpdated(traineeUpdateRequest, trainee);

        return ResponseEntity.ok(traineeDTO);
    }

    // modify to GET method when I can authorize based on session
    @PostMapping("/trainee-delete")
    public ResponseEntity<?> deleteTrainee(@Valid @RequestBody UsernamePassword usernamePassword) {

        String username = usernamePassword.getUsername();
        String password = usernamePassword.getPassword();
        traineeService.delete(username, password);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/trainee/update-trainers-list")
    public ResponseEntity<?> updateTrainersList(
            @Valid @RequestBody TraineeUpdateTrainersListRequest traineeUpdateTrainersListRequest) {

        String username = traineeUpdateTrainersListRequest.getUsername();
        String password = traineeUpdateTrainersListRequest.getPassword();
        List<String> trainerUsernames = traineeUpdateTrainersListRequest.getTrainerUsernames();

        Optional<Trainee> traineeOptional = traineeService.findByUsernameAndPassword(username, password);
        if (traineeOptional.isEmpty()) {
            throw new InvalidRequestBodyException("username or password is invalid");
        }

        Trainee trainee = traineeOptional.get();
        List<TrainerDTOForTrainersList> trainerListDTO = getTrainerListDTO(trainerUsernames, trainee);

        return ResponseEntity.ok().body(trainerListDTO);
    }

    @PatchMapping("/trainee/activate")
    public ResponseEntity<?> activateTrainee(@Valid @RequestBody UsernamePassword usernamePassword) {
        String username = usernamePassword.getUsername();

        Optional<Trainee> traineeOptional = traineeService.getByUsername(username);
        if (traineeOptional.isEmpty()) {
            throw new InvalidRequestBodyException("Could not find trainee");
        }
        Trainee trainee = traineeOptional.get();
        trainee.setIsActive(true);
        traineeService.update(trainee);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/trainee/deactivate")
    public ResponseEntity<?> deactivateTrainee(@Valid @RequestBody UsernamePassword usernamePassword) {

        String username = usernamePassword.getUsername();

        Optional<Trainee> traineeOptional = traineeService.getByUsername(username);
        if (traineeOptional.isEmpty()) {
            throw new InvalidRequestBodyException("Could not find trainee");
        }
        Trainee trainee = traineeOptional.get();
        trainee.setIsActive(false);
        traineeService.update(trainee);
        return ResponseEntity.ok().build();
    }

    private static TraineeDTOWithTrainersList getTraineeDTOWithTrainersList(Trainee trainee) {
        TraineeDTOWithTrainersList traineeDTO = new TraineeDTOWithTrainersList();
        traineeDTO.setFirstName(trainee.getFirstName());
        traineeDTO.setLastName(trainee.getLastName());
        traineeDTO.setDateOfBirth(trainee.getDob());
        traineeDTO.setAddress(trainee.getAddress());
        traineeDTO.setActive(trainee.getIsActive());

        List<TrainerDTOForTrainersList> trainerList = trainee
                .getTrainers()
                .stream()
                .map(trainer -> {
                    TrainerDTOForTrainersList trainerDTOForTrainersList = new TrainerDTOForTrainersList();
                    trainerDTOForTrainersList.setUsername(trainer.getUsername());
                    trainerDTOForTrainersList.setFirstName(trainer.getFirstName());
                    trainerDTOForTrainersList.setLastName(trainer.getLastName());
                    trainerDTOForTrainersList.setSpecialization(trainer.getSpecialization());
                    return trainerDTOForTrainersList;
                }).toList();

        traineeDTO.setTrainers(trainerList);
        return traineeDTO;
    }

    private TraineeDTOUpdated getTraineeDTOUpdated(TraineeUpdateRequest traineeUpdateRequest, Trainee trainee) {
        trainee.setFirstName(traineeUpdateRequest.getFirstName());
        trainee.setLastName(traineeUpdateRequest.getLastName());
        trainee.setDob(traineeUpdateRequest.getDateOfBirth());
        trainee.setAddress(traineeUpdateRequest.getAddress());
        trainee.setIsActive(traineeUpdateRequest.getIsActive());

        Optional<Trainee> updatedTraineeOptional = traineeService.update(trainee);
        if (updatedTraineeOptional.isEmpty()) {
            throw new RuntimeException("Could not update trainee");
        }
        Trainee updatedTrainee = updatedTraineeOptional.get();
        TraineeDTOUpdated traineeDTO = new TraineeDTOUpdated();
        traineeDTO.setUsername(updatedTrainee.getUsername());
        traineeDTO.setFirstName(updatedTrainee.getFirstName());
        traineeDTO.setLastName(updatedTrainee.getLastName());
        traineeDTO.setDateOfBirth(updatedTrainee.getDob());
        traineeDTO.setAddress(updatedTrainee.getAddress());
        traineeDTO.setIsActive(updatedTrainee.getIsActive());

        List<TrainerDTOForTrainersList> trainerList = updatedTrainee
                .getTrainers()
                .stream()
                .map(trainer -> {
                    TrainerDTOForTrainersList trainerDTOForTrainersList = new TrainerDTOForTrainersList();
                    trainerDTOForTrainersList.setUsername(trainer.getUsername());
                    trainerDTOForTrainersList.setFirstName(trainer.getFirstName());
                    trainerDTOForTrainersList.setLastName(trainer.getLastName());
                    trainerDTOForTrainersList.setSpecialization(trainer.getSpecialization());
                    return trainerDTOForTrainersList;
                }).toList();

        traineeDTO.setTrainers(trainerList);
        return traineeDTO;
    }

    private List<TrainerDTOForTrainersList> getTrainerListDTO(
            List<String> trainerUsernames,
            Trainee trainee) {

        List<Trainer> trainers = trainerUsernames
                .stream()
                .map(trainerUsername -> {
                    Optional<Trainer> trainerOptional = trainerService.getByUsername(trainerUsername);
                    if (trainerOptional.isEmpty()) {
                        throw new InvalidRequestBodyException("Could not find trainer");
                    }
                    return trainerOptional.get();
                }).toList();
        Set<Trainer> trainerSet = new HashSet<Trainer>(trainers);
        trainee.setTrainers(trainerSet);
        traineeService.update(trainee);

        return trainerSet
                .stream()
                .map(trainer -> {
                    return new TrainerDTOForTrainersList(
                            trainer.getUsername(),
                            trainer.getFirstName(),
                            trainer.getLastName(),
                            trainer.getSpecialization());
                }).toList();
    }
}
