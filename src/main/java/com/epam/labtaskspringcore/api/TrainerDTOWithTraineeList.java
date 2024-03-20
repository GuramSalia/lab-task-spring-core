package com.epam.labtaskspringcore.api;

import com.epam.labtaskspringcore.model.TrainingType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class TrainerDTOWithTraineeList {

    private String firstName;
    private String lastName;
    private TrainingType specialization;
    private Boolean isActive;
    Set<TraineeDTOForTraineesList> trainees;

    public TrainerDTOWithTraineeList(
            String firstName,
            String lastName,
            TrainingType specialization,
            Boolean isActive,
            Set<TraineeDTOForTraineesList> trainees) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.isActive = isActive;
        this.trainees = trainees;
    }

    public TrainerDTOWithTraineeList() {
    }
}
