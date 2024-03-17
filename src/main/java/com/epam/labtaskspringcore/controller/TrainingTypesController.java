package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.aspect.LogRestDetails;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.service.TrainingTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@LogRestDetails
@RestController
public class TrainingTypesController {

    private final TrainingTypeService trainingTypeService;

    @Autowired
    public TrainingTypesController(TrainingTypeService trainingTypeService) {this.trainingTypeService = trainingTypeService;}

    @GetMapping("/training-types")
    public ResponseEntity<?> getTrainingTypes(HttpServletRequest request,
                                              HttpServletResponse response) {
        List<TrainingType> trainingTypes = trainingTypeService.getAllTrainingTypes();
        return ResponseEntity.ok(trainingTypes);

//    a.	Request -no data
//    b.	Response
//        I.	Training types
//           1.	Training type
//           2.	Training type Id


    }
}
