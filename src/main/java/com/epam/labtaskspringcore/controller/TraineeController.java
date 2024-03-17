package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.aspect.CheckUsernamePassword;
import com.epam.labtaskspringcore.aspect.LogRestDetails;
import com.epam.labtaskspringcore.payloads.TraineeRegistrationRequest;
import com.epam.labtaskspringcore.payloads.TraineeUpdateRequest;
import com.epam.labtaskspringcore.payloads.UsernamePassword;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CheckUsernamePassword
@LogRestDetails
@RestController
public class TraineeController {

    @GetMapping("/trainee")
    public ResponseEntity<?> getTrainee(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestBody UsernamePassword usernamePassword) {

        //     a.	Request
        //        I.	Username (required)
        //        II.    Password (required)
        //
        //     b.	Response
        //        I.	First Name
        //        II.	Last Name
        //        III.	Date of Birth
        //        IV.	Address
        //        V.	Is Active
        //        VI.	Trainers List
        //           1.	Trainer Username
        //           2.	Trainer First Name
        //           3.	Trainer Last Name
        //           4.	Trainer Specialization (Training type reference)

        return null;
    }

    @PutMapping("/trainee")
    public ResponseEntity<?> updateTrainee(HttpServletRequest request,
                                           HttpServletResponse response,
                                           @RequestBody TraineeUpdateRequest traineeUpdateRequest) {

        //    a.	Request
        //        I.	Username (required)
        //        II.    Password (required)
        //        III.    First Name (required)
        //        II.	First Name (required)
        //        III.	Last Name (required)
        //        IV.	Date of Birth (optional)
        //        V.	Address (optional)
        //        VI.	Is Active (required)
        //    b.	Response
        //        I.	Username
        //        II.	First Name
        //        III.	Last Name
        //        IV.	Date of Birth
        //        V.	Address
        //        VI.	Is Active
        //        VII.	Trainers List
        //            1.	Trainer Username
        //            2.	Trainer First Name
        //            3.	Trainer Last Name
        //            4.	Trainer Specialization (Training type reference)

        return null;
    }

    @DeleteMapping("/trainee")
    public ResponseEntity<?> deleteTrainee(HttpServletRequest request,
                                           HttpServletResponse response,
                                           @RequestBody UsernamePassword usernamePassword) {

        //     a.	Request
        //        I.	Username (required)
        //        II.    Password (required)
        //     b.	Response
        //        I.	200 OK

        return null;
    }

    @PutMapping("/trainee/update-trainers-list")
    public ResponseEntity<?> updateTrainersList(HttpServletRequest request,
                                                HttpServletResponse response,
                                                @RequestBody UsernamePassword usernamePassword) {

        //    a.	Request
        //        I.	Trainee Username
        //        II.   Trainee Password
        //        III.	Trainers List (required)
        //                1.	Trainer Username (required)
        //    b.	Response
        //        I.	Trainers List
        //        1.	Trainer Username
        //        2.	Trainer First Name
        //        3.	Trainer Last Name
        //        4.	Trainer Specialization (Training type reference)

        return null;
    }

    @PatchMapping("/trainee/activate")
    public ResponseEntity<?> activateTrainee(HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestBody UsernamePassword usernamePassword) {

        //     a.	Request
        //        I.	Username (required)
        //        II.    Password (required)
        //     b.	Response
        //        I.	200 OK

        return null;
    }

    @PatchMapping("/trainee/deactivate")
    public ResponseEntity<?> deactivateTrainee(HttpServletRequest request,
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
