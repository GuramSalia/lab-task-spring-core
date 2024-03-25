package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.aspect.CheckUsernamePassword;
import com.epam.labtaskspringcore.aspect.LogRestDetails;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.api.UsernamePassword;
import com.epam.labtaskspringcore.service.TrainingTypeService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    private final Counter training_types_get_requests_success_counter;

    @Autowired
    public TrainingTypesController(
            TrainingTypeService trainingTypeService,
            MeterRegistry meterRegistry) {
        this.trainingTypeService =
                trainingTypeService;
        this.training_types_get_requests_success_counter = Counter
                .builder("training_types_get_requests_success_counter")
                .description("number of successful hits: GET /training-types")
                .register(meterRegistry);
    }

    @GetMapping("/training-types")
    @Operation(summary = "get training types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee activated successfully")
    })
    public ResponseEntity<List<TrainingType>> getTrainingTypes(@Valid @RequestBody UsernamePassword usernamePassword) {
        List<TrainingType> trainingTypes = trainingTypeService.getAllTrainingTypes();
        training_types_get_requests_success_counter.increment();
        return ResponseEntity.ok(trainingTypes);
    }
}
