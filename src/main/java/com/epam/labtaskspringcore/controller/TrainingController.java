package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.aspect.LogRestDetails;
import com.epam.labtaskspringcore.payloads.TrainingRegistrationRequest;
import com.epam.labtaskspringcore.payloads.TrainingsByTrainee;
import com.epam.labtaskspringcore.payloads.TrainingsByTrainer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@LogRestDetails
@RestController
public class TrainingController {

    @PostMapping("/training")
    public ResponseEntity<?> registerTraining(HttpServletRequest request,
                                              HttpServletResponse response,
                                              @RequestParam TrainingRegistrationRequest trainingRegistrationRequest) {
        //        a.	Request
        //        I.	Trainee username (required)
        //        II.	Trainer username (required)
        //        III.	Training Name (required)
        //        IV.	Training Date (required)
        //        V.	Training Duration (required)
        //                b.	Response
        //        I.	200 OK

        return null;
    }

    @GetMapping("/training/of-trainee")
    public ResponseEntity<?> getTrainingsByTraineeAndOtherFilters(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam TrainingsByTrainee trainingsByTrainee) {

        //    a.	Request
        //        I.	Username (required)
        //        I.    Password (required)
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

        return null;
    }

    @GetMapping("/training/of-trainer")
    public ResponseEntity<?> getTrainingsByTrainerAndOtherFilters(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam TrainingsByTrainer trainingsByTrainer) {

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

        return null;
    }
}
