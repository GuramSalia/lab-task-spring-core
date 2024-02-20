package com.epam.labtaskspringcore.model;

import java.util.Date;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Trainee extends User {
    private int traineeId;
    private String Address;
    private Date dob;

    @Override
    public String toString() {
        return "Trainer{" +
                "traineeId=" + traineeId +
                ", first-name='" + getFirstName() + '\'' +
                ", last-name='" + getLastName() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainee trainee = (Trainee) o;
        return getTraineeId() == trainee.getTraineeId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTraineeId(), getFirstName(), getLastName());
    }
}
