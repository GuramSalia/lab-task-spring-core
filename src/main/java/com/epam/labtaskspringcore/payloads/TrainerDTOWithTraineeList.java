package com.epam.labtaskspringcore.payloads;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.TrainingType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
public class TrainerDTOWithTraineeList {
    //     b.	Response
    //        I.	First Name
    //        II.	Last Name
    //        III.	Specialization (Training type reference)
    //        IV.	Is Active
    //        V.	Trainees List
    //           1.	Trainee Username
    //           2.	Trainee First Name
    //           3.	Trainee Last Name

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
