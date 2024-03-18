package com.epam.labtaskspringcore.payloads;

import com.epam.labtaskspringcore.model.TrainingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TrainerDTOupdated {

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

    private String username;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private TrainingType specialization;
    @NotNull
    private Boolean isActive;
    Set<TraineeDTOForTraineesList> trainees;

    public TrainerDTOupdated(
            String username,
            String firstName,
            String lastName,
            TrainingType specialization,
            Boolean isActive,
            Set<TraineeDTOForTraineesList> trainees) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.isActive = isActive;
        this.trainees = trainees;
    }

    public TrainerDTOupdated() {
    }
}
