package com.epam.labtaskspringcore.payloads;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TraineeDTOWithTrainersList {

    //     b.	Response
    //        I.	First Name
    //        II.	Last Name
    //        III.	Date of Birth
    //        IV.	Address
    //        V.	Is Active
    //        VI.	Trainers List
    //           1.	Trainer Username
    //           2.	Trainer First Name
    //           3.	Trainer Last Name
    //           4.	Trainer Specialization (Training type reference)

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String address;
    private boolean isActive;
    private List<TrainerDTOForTrainersList> trainers;

    public TraineeDTOWithTrainersList(
            String firstName,
            String lastName,
            Date dateOfBirth,
            String address,
            boolean isActive,
            List<TrainerDTOForTrainersList> trainers) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.isActive = isActive;
        this.trainers = trainers;
    }

    public TraineeDTOWithTrainersList() {
    }

    @Override
    public String toString() {
        return "TraineeDTOWithTrainersList{" +
                "trainers=" + trainers +
                "} " + super.toString();
    }
}
