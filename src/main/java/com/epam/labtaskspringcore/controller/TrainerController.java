package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.aspect.CheckUsernamePassword;
import com.epam.labtaskspringcore.aspect.LogRestDetails;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.payloads.*;
import com.epam.labtaskspringcore.service.TrainerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CheckUsernamePassword
@LogRestDetails
@RestController
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping("/trainer-get")
    public ResponseEntity<?> getTrainer(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestBody UsernamePassword usernamePassword) {

        //     a.	Request
        //        I.	Username (required)
        //        II.   Password (required)
        //     b.	Response
        //        I.	First Name
        //        II.	Last Name
        //        III.	Specialization (Training type reference)
        //        IV.	Is Active
        //        V.	Trainees List
        //           1.	Trainee Username
        //           2.	Trainee First Name
        //           3.	Trainee Last Name

        String username = usernamePassword.getUsername();
        Optional<Trainer> trainerOptional = trainerService.getByUsername(username);

        if (trainerOptional.isEmpty()) {
            throw new IllegalArgumentException("no trainer found");
        }
        Trainer trainer = trainerOptional.get();

        return getTrainerDTOWithTraineeListResponseEntity(trainer);
    }

    private static ResponseEntity<TrainerDTOWithTraineeList> getTrainerDTOWithTraineeListResponseEntity(
            Trainer trainer) {

        TrainerDTOWithTraineeList trainerDTO = new TrainerDTOWithTraineeList();
        trainerDTO.setFirstName(trainer.getFirstName());
        trainerDTO.setLastName(trainer.getLastName());
        trainerDTO.setSpecialization(trainer.getSpecialization());
        trainerDTO.setIsActive(trainer.getIsActive());

        List<TraineeDTOForTraineesList> trainees;
        if (trainerDTO.getTrainees()!=null) {
            trainees = trainerDTO
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

        return ResponseEntity.ok().body(trainerDTO);
    }

    @PutMapping("/trainer")
    public ResponseEntity<?> updateTrainer(HttpServletRequest request,
                                           HttpServletResponse response,
                                           @RequestBody TrainerUpdateRequest trainerUpdateRequest) {

        //   a.	Request
        //        I.	Username (required)
        //        II.   Password (required)
        //        III.  First Name (required)
        //        III.	Last Name (required)
        //        IV.	Specialization (read only) (Training type reference)
        //        V.	Is Active (required)
        //    b.	Response
        //        I.	Username
        //        II.	First Name
        //        III.	Last Name
        //        IV.	Specialization (Training type reference)
        //        V.	Is Active
        //        VI.	Trainees List
        //           1.	Trainee Username
        //           2.	Trainee First Name
        //           3.	Trainee Last Name

        return null;
    }

    @GetMapping("/trainers/not-assigned-to-trainee")
    public ResponseEntity<?> getNotAssignedTrainers(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    @RequestBody UsernamePassword usernamePassword) {
        //    a.	Request
        //        I.	Username (required)
        //        II.   Password (required)
        //    b.	Response
        //        I.	Trainer Username
        //        II.	Trainer First Name
        //        III.	Trainer Last Name
        //        IV.	Trainer Specialization (Training type reference)

        return null;
    }

    @PatchMapping("/trainer/activate")
    public ResponseEntity<?> activateTrainer(HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestBody UsernamePassword usernamePassword) {

        //     a.	Request
        //        I.	Username (required)
        //        II.    Password (required)
        //     b.	Response
        //        I.	200 OK

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
    public ResponseEntity<?> deactivateTrainer(HttpServletRequest request,
                                               HttpServletResponse response,
                                               @RequestBody UsernamePassword usernamePassword) {

        //     a.	Request
        //        I.	Username (required)
        //        II.    Password (required)
        //     b.	Response
        //        I.	200 OK

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
}
