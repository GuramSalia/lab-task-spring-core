package com.epam.labtaskspringcore.payloads;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class TraineeDTOUpdated {
    //    b.	Response
    //        I.	Username
    //        II.	First Name
    //        III.	Last Name
    //        IV.	Date of Birth
    //        V.	Address
    //        VI.	Is Active
    //        VII.	Trainers List
    //            1.	Trainer Username
    //            2.	Trainer First Name
    //            3.	Trainer Last Name
    //            4.	Trainer Specialization (Training type reference)

    private String username;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String address;
    private Boolean isActive;
    private List<TrainerDTOForTrainersList> trainers;

    public TraineeDTOUpdated(
            String username,
            String firstName,
            String lastName,
            Date dateOfBirth,
            String address,
            boolean isActive,
            List<TrainerDTOForTrainersList> trainers) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.isActive = isActive;
        this.trainers = trainers;
    }

    public TraineeDTOUpdated() {
    }
}
