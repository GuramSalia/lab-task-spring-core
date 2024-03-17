package com.epam.labtaskspringcore.payloads;

import com.epam.labtaskspringcore.model.TrainingType;
import lombok.Getter;

import java.sql.Date;

@Getter
public class TrainingsByTraineeRequest extends UsernamePassword {

    //    a.	Request
    //        I.	Username (required)
    //        I.    Password (required)
    //        II.	Period From (optional)
    //        III.	Period To (optional)
    //        IV.	Trainer Name (optional)
    //        V.	Training Type (optional)

    Date periodFrom;
    Date periodTo;
    String trainerUsername;
    TrainingType trainingType;


    public TrainingsByTraineeRequest(
            String username,
            String password,
            Date periodFrom,
            Date periodTo,
            String trainerUsername,
            TrainingType trainingType) {
        super(username, password);
        this.periodFrom = periodFrom;
        this.periodTo = periodTo;
        this.trainerUsername = trainerUsername;
        this.trainingType = trainingType;
    }

    @Override
    public String toString() {
        return "TrainingsByTrainee{" +
                "periodFrom=" + periodFrom +
                ", periodTo=" + periodTo +
                ", trainerUsername='" + trainerUsername + '\'' +
                ", trainingType=" + trainingType +
                ", username='" + username + '\'' +
                '}';
    }
}
