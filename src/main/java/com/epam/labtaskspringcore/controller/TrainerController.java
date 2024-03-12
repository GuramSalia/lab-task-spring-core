package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.payloads.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TrainerController {

    @PostMapping("/trainer")
    public ResponseEntity<?> registerTrainer(HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestBody TrainerRegistrationRequest trainerRegistrationRequest) {

//    a.	Request
//        I.	First Name (required)
//        II.	Last Name (required)
//        III.	Specialization (required) (Training type reference)
//    b.	Response
//        I.	Username
//        II.	Password



        return null;
    }

    @GetMapping("/trainer")
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
//        1.	Trainee Username
//        2.	Trainee First Name
//        3.	Trainee Last Name


        return null;
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

        return null;
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

        return null;
    }

}
