package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.aspect.LogRestDetails;
import com.epam.labtaskspringcore.payloads.TraineeRegistrationRequest;
import com.epam.labtaskspringcore.payloads.TrainerRegistrationRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@LogRestDetails
@RestController
public class CreateUserController {


    @PostMapping("/trainee")
    public ResponseEntity<?> registerTrainee(HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestBody TraineeRegistrationRequest traineeRegistrationRequest) {

        //     a.	Request
        //        I.	First Name (required)
        //        II.	Last Name (required)
        //        III.	Date of Birth (optional)
        //        IV.	Address (optional)
        //     b.	Response
        //        I.	Username
        //        II.	Password

        return null;
    }


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




}
