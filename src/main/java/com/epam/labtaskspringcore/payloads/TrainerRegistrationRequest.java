package com.epam.labtaskspringcore.payloads;

import com.epam.labtaskspringcore.model.TrainingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerRegistrationRequest {

    //    a.	Request
    //        I.	First Name (required)
    //        II.	Last Name (required)
    //        III.	Specialization (required) (Training type reference)

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private String specialization;

    public TrainerRegistrationRequest(
            String firstName,
            String lastName,
            String specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }
}
