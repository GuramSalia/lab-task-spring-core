package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.aspect.CheckUsernamePassword;
import com.epam.labtaskspringcore.aspect.LogRestDetails;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.payloads.UsernamePassword;
import com.epam.labtaskspringcore.service.TrainingTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@LogRestDetails
@RestController
@CheckUsernamePassword
public class TrainingTypesController {

    private final TrainingTypeService trainingTypeService;

    @Autowired
    public TrainingTypesController(TrainingTypeService trainingTypeService) {
        this.trainingTypeService =
                trainingTypeService;
    }

    // modify to GET method when I can authorize based on session
    @PostMapping("/training-types")
    public ResponseEntity<?> getTrainingTypes(@Valid @RequestBody UsernamePassword usernamePassword) {

        List<TrainingType> trainingTypes = trainingTypeService.getAllTrainingTypes();
        return ResponseEntity.ok(trainingTypes);
    }
}
