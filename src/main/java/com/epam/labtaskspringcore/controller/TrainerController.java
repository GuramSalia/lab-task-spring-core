package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.api.*;
import com.epam.labtaskspringcore.aspect.CheckUsernamePassword;
import com.epam.labtaskspringcore.aspect.LogRestDetails;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.service.TrainerService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
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

    private final Counter trainer_get_requests_success_counter;
    private final Counter trainer_put_requests_success_counter;
    private final Counter trainers_get_not_assigned_to_trainee_requests_success_counter;
    private final Counter trainer_activate_patch_requests_success_counter;
    private final Counter trainer_deactivate_patch_requests_success_counter;

    public TrainerController(
            TrainerService trainerService,
            MeterRegistry meterRegistry) {
        this.trainerService = trainerService;
        this.trainer_get_requests_success_counter = Counter
                .builder("trainer_get_requests_success_counter")
                .description("number of successful hits: GET /trainer-get")
                .register(meterRegistry);
        this.trainer_put_requests_success_counter = Counter
                .builder("trainer_put_requests_success_counter")
                .description("number of successful hits: PUT /trainer")
                .register(meterRegistry);
        this.trainers_get_not_assigned_to_trainee_requests_success_counter = Counter
                .builder("trainers_get_not_assigned_to_trainee_requests_success_counter")
                .description("number of successful hits: GET /trainers/get-not-assigned-to-trainee")
                .register(meterRegistry);
        this.trainer_activate_patch_requests_success_counter = Counter
                .builder("trainer_activate_patch_requests_success_counter")
                .description("number of successful hits: PATCH /trainer/activate")
                .register(meterRegistry);
        this.trainer_deactivate_patch_requests_success_counter = Counter
                .builder("trainer_deactivate_patch_requests_success_counter")
                .description("number of successful hits: PATCH /trainer/deactivate")
                .register(meterRegistry);
    }

    @GetMapping("/trainer-get")
    @Operation(summary = "Get Trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainer")
    })
    public ResponseEntity<TrainerDTOWithTraineeList> getTrainer(@Valid @RequestBody UsernamePassword usernamePassword) {

        String username = usernamePassword.getUsername();
        Optional<Trainer> trainerOptional = Optional.ofNullable(trainerService.findByUsername(username));

        if (trainerOptional.isEmpty()) {
            throw new IllegalArgumentException("no trainer found");
        }
        Trainer trainer = trainerOptional.get();

        TrainerDTOWithTraineeList trainerDTO = getTrainerDTOWithTraineeList(trainer);
        trainer_get_requests_success_counter.increment();
        return ResponseEntity.ok().body(trainerDTO);
    }

    @PutMapping("/trainer")
    @Operation(summary = "Update Trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer updated successfully")
    })
    public ResponseEntity<TrainerDTOupdated> updateTrainer(@Valid @RequestBody TrainerUpdateRequest trainerUpdateRequest) {

        String username = trainerUpdateRequest.getUsername();
        Optional<Trainer> trainerOptional = Optional.ofNullable(trainerService.findByUsername(username));
        if (trainerOptional.isEmpty()) {
            throw new IllegalArgumentException("no trainer found");
        }

        Trainer trainer = trainerOptional.get();
        TrainerDTOupdated trainerDTOupdated = getTrainerDTOupdated(trainerUpdateRequest, trainer);
        trainer_put_requests_success_counter.increment();
        return ResponseEntity.ok().body(trainerDTOupdated);
    }

    @GetMapping("/trainers/get-not-assigned-to-trainee")
    @Operation(summary = "Get Trainers Not Assigned to Trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainers")
    })
    public ResponseEntity<List<TrainerDTOForTrainersList>> getNotAssignedTrainers(@Valid @RequestBody UsernamePassword usernamePassword) {

        String username = usernamePassword.getUsername();
        String password = usernamePassword.getPassword();
        List<Trainer> trainerList = trainerService.findUnassignedTrainersByTraineeUsername(username, password);

        List<TrainerDTOForTrainersList> trainerDTOs = getTrainerDTOForTrainersLists(trainerList);
        trainers_get_not_assigned_to_trainee_requests_success_counter.increment();
        return ResponseEntity.ok().body(trainerDTOs);
    }

    @PatchMapping("/trainer/activate")
    @Operation(summary = "Activate Trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Trainer activated successfully")
    })
    public ResponseEntity<Void> activateTrainer(@Valid @RequestBody UsernamePassword usernamePassword) {
        String username = usernamePassword.getUsername();
        Trainer trainer = trainerService.findByUsername(username);
        trainer.setIsActive(true);
        trainerService.update(trainer);
        trainer_activate_patch_requests_success_counter.increment();
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
    }

    @PatchMapping("/trainer/deactivate")
    @Operation(summary = "Deactivate Trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Trainer deactivated successfully")
    })
    public ResponseEntity<Void> deactivateTrainer(@Valid @RequestBody UsernamePassword usernamePassword) {
        String username = usernamePassword.getUsername();
        Trainer trainer = trainerService.findByUsername(username);
        trainer.setIsActive(false);
        trainerService.update(trainer);
        trainer_deactivate_patch_requests_success_counter.increment();
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
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

        Optional<Trainer> updatedTrainerOptional = Optional.ofNullable(trainerService.update(trainer));
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
