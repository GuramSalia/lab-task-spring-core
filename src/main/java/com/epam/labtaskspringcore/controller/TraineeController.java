package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.aspect.CheckUsernamePassword;
import com.epam.labtaskspringcore.aspect.LogRestDetails;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.api.*;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
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



    @GetMapping("/trainee-get")
    @Operation(summary = "Get Trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee retrieved successfully")
    })
    public ResponseEntity<TraineeDTOWithTrainersList> getTrainee(@Valid @RequestBody UsernamePassword usernamePassword) {
        String username = usernamePassword.getUsername();
        String password = usernamePassword.getPassword();
        Trainee trainee = traineeService.findByUsernameAndPassword(username, password);
        TraineeDTOWithTrainersList traineeDTO = getTraineeDTOWithTrainersList(trainee);
        return ResponseEntity.ok(traineeDTO);
    }


    @PutMapping("/trainee")
    @Operation(summary = "Update Trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee updated successfully")
    })
    public ResponseEntity<TraineeDTOUpdated> updateTrainee(@Valid @RequestBody TraineeUpdateRequest traineeUpdateRequest) {
        String username = traineeUpdateRequest.getUsername();
        String password = traineeUpdateRequest.getPassword();
        Trainee trainee = traineeService.findByUsernameAndPassword(username, password);
        TraineeDTOUpdated traineeDTO = getTraineeDTOUpdated(traineeUpdateRequest, trainee);
        return ResponseEntity.ok(traineeDTO);
    }



    @DeleteMapping("/trainee-delete")
    @Operation(summary = "Delete Trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Trainee deleted successfully")
    })
    public ResponseEntity<Void> deleteTrainee(@Valid @RequestBody UsernamePassword usernamePassword) {
        String username = usernamePassword.getUsername();
        String password = usernamePassword.getPassword();
        traineeService.delete(username, password);
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
    }

    @PutMapping("/trainee/update-trainers-list")
    @Operation(summary = "Update Trainee's Trainers List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainers list updated successfully")
    })
    public ResponseEntity<List<TrainerDTOForTrainersList>> updateTrainersList(
            @Valid @RequestBody TraineeUpdateTrainersListRequest traineeUpdateTrainersListRequest) {

        String username = traineeUpdateTrainersListRequest.getUsername();
        String password = traineeUpdateTrainersListRequest.getPassword();
        List<String> trainerUsernames = traineeUpdateTrainersListRequest.getTrainerUsernames();
        Trainee trainee = traineeService.findByUsernameAndPassword(username, password);
        List<TrainerDTOForTrainersList> trainerListDTO = getTrainerListDTO(trainerUsernames, trainee);
        return ResponseEntity.ok().body(trainerListDTO);
    }

    @PatchMapping("/trainee/activate")
    @Operation(summary = "Activate Trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Trainee activated successfully")
    })
    public ResponseEntity<Void> activateTrainee(@Valid @RequestBody UsernamePassword usernamePassword) {
        String username = usernamePassword.getUsername();
        String password = usernamePassword.getPassword();
        Trainee trainee = traineeService.findByUsernameAndPassword(username, password);
        trainee.setIsActive(true);
        traineeService.update(trainee);
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
    }

    @PatchMapping("/trainee/deactivate")
    @Operation(summary = "Deactivate Trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Trainee deactivated successfully")
    })
    public ResponseEntity<Void> deactivateTrainee(@Valid @RequestBody UsernamePassword usernamePassword) {
        String username = usernamePassword.getUsername();
        String password = usernamePassword.getPassword();
        Trainee trainee = traineeService.findByUsernameAndPassword(username, password);
        trainee.setIsActive(false);
        traineeService.update(trainee);
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
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

        Trainee updatedTrainee = traineeService.update(trainee);

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
                .map(trainerService::findByUsername).toList();
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
