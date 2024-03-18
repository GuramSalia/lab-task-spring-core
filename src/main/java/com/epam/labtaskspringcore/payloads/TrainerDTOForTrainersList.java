package com.epam.labtaskspringcore.payloads;

import com.epam.labtaskspringcore.model.TrainingType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerDTOForTrainersList {

    //1.	Trainer Username
    //2.	Trainer First Name
    //3.	Trainer Last Name
    //4.	Trainer Specialization (Training type reference)

    private String username;
    private String firstName;
    private String lastName;
    private TrainingType specialization;

    public TrainerDTOForTrainersList(String username, String firstName, String lastName, TrainingType specialization) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }

    public TrainerDTOForTrainersList() {
    }

    @Override
    public String toString() {
        return "TrainerDTOForTrainersList{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialization=" + specialization +
                '}';
    }
}
